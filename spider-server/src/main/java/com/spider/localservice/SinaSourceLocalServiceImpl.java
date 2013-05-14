package com.spider.localservice;

import com.spider.config.SourceEnum;
import com.wolf.browser.BrowserProxySessionBeanRemote;
import com.wolf.browser.BrowserProxySessionBeanRemoteFactory;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author aladdin
 */
@LocalServiceConfig(
        interfaceInfo = SinaSourceLocalService.class,
description = "负责获取新浪的信息")
public class SinaSourceLocalServiceImpl implements SinaSourceLocalService {

    private final int maxPageIndex = 50;
    private final SourceEnum sourceEnum = SourceEnum.SINA;
    private final String rootUrl = "http://weibo.com/";
    private final String searchUrl = "http://s.weibo.com/user/";
    private final String infoPath = "${id}/info?from=profile&wvr=5&loc=tabinf&ajaxpagelet=1";
    private final String followPath = "${id}/follow?from=rel&wvr=5&loc=hisfollow";
    private final Map<String, String> regionMap = new HashMap<String, String>(16, 1);
    private final Pattern filterPattern = Pattern.compile("\\\\t|\\\\n|\\\\r|\\\\");
    private final Pattern searchIdPattern = Pattern.compile("(?:uid=\")(\\d*)");
    private final Pattern replaceIdPattern = Pattern.compile("\\$\\{id\\}");
    private final Pattern infoValuePattern = Pattern.compile("(?:\"con\">)([\\w\\W]*?)(?:<)");
    private final Pattern infoLablePattern = Pattern.compile("(?:\"label S_txt2\">)([\\w\\W]*?)(?:<)");
    private final Pattern infoTagPattern = Pattern.compile("(?:\"tag\">)([\\w\\W]*?)(?:<)");
    private final Pattern followPattern = Pattern.compile("(?:\"id=)(\\d*)");
    private final Map<String, String> attrNameMap = new HashMap<String, String>(4, 1);
    private final String[] attrNames = {"nickName", "location", "gender", "empName"};
    //
    private final String userNameXPath = "/html/body/div/div[2]/div[2]/div[2]/div/div/div/input";
    private final String passwordXPath = "/html/body/div/div[2]/div[2]/div[2]/div/div[2]/div/input";
    private final String checkCodeXPath = "/html/body/div/div[2]/div[2]/div[2]/div/div[3]/div/input";
    private final int checkCodeLength = 5;
    private final String loginBtnXPath = "/html/body/div/div[2]/div[2]/div[2]/div/div[6]/a/span";
    //
    @InjectLocalService()
    private HttpClientLocalService httpClientLocalService;

    public SinaSourceLocalServiceImpl() {
        //
        this.regionMap.put("福建", "custom:35:1000");
        this.regionMap.put("北京", "custom:11:1000");
        this.regionMap.put("上海", "custom:31:1000");
        this.regionMap.put("广州", "custom:44:1");
        this.regionMap.put("深圳", "custom:44:3");
        //
        attrNameMap.put("昵称", "nickName");
        attrNameMap.put("所在地", "location");
        attrNameMap.put("性别", "gender");
        attrNameMap.put("真实姓名", "empName");
    }

    @Override
    public void init() {
    }

    /**
     * get 请求
     *
     * @param url
     * @return
     */
    private String get(String url) {
        String response = this.httpClientLocalService.get(this.sourceEnum, url);
        System.out.println(url);
        return response;
    }

    private String filter(String text) {
        return this.filterPattern.matcher(text).replaceAll("");
    }

    @Override
    public String getSearchText(String location, String tag, int pageIndex) {
        String result = "";
        if (pageIndex <= this.maxPageIndex) {
            StringBuilder urlBuilder = new StringBuilder(32);
            urlBuilder.append(this.searchUrl).append("&tag=").append(tag);
            String cumtom = this.regionMap.get(location);
            if (cumtom != null) {
                urlBuilder.append("&region=").append(cumtom);
            }
            urlBuilder.append("&page=").append(pageIndex);
            String url = urlBuilder.toString();
            String response = this.get(url);
            int index = response.lastIndexOf("pl_user_feedList");
            if (index == -1) {
                System.err.println(url);
                System.err.println("访问出现异常");
                System.err.println(response);
            } else {
                result = response;
            }
        }
        return result;
    }

    @Override
    public List<String> parseSearchText(String text) {
        List<String> idList;
        int index = text.lastIndexOf("pl_user_feedList");
        if (index == -1) {
            idList = new ArrayList<String>(0);
        } else {
            //过滤
            text = text.substring(index);
            index = text.indexOf("</script>");
            text = text.substring(0, index);
            text = this.filter(text);
            Matcher matcher = this.searchIdPattern.matcher(text);
            Set<String> idSet = new HashSet<String>(64, 1);
            String id;
            while (matcher.find()) {
                id = matcher.group(1);
                idSet.add(id);
            }
            idList = new ArrayList<String>(idSet.size());
            idList.addAll(idSet);
        }
        return idList;
    }

    @Override
    public String getInfoText(String sourceId) {
        String result = "";
        String path = this.replaceIdPattern.matcher(this.infoPath).replaceFirst(sourceId);
        String url = this.rootUrl.concat(path);
        String response = this.get(url);
        int index = response.lastIndexOf("pl_profile_infoBase");
        if (index == -1) {
            System.err.println(url);
            System.err.println("访问出现异常");
            System.err.println(response);
        } else {
            result = response;
        }
        return result;
    }

    @Override
    public Map<String, String> parseInfoText(String text) {
        Map<String, String> resultMap;
        int index = text.lastIndexOf("pl_profile_infoBase");
        if (index == -1) {
            resultMap = null;
        } else {
            resultMap = new HashMap<String, String>(8, 1);
            text = text.substring(index);
            String infoBase = text;
            index = infoBase.indexOf("</script>");
            infoBase = infoBase.substring(0, index);
            infoBase = this.filter(infoBase);
            //获取label
            Matcher infoLabelMatcher = this.infoLablePattern.matcher(infoBase);
            List<String> labelList = new ArrayList<String>(8);
            while (infoLabelMatcher.find()) {
                labelList.add(infoLabelMatcher.group(1));
            }
            //获取label value
            Matcher infoValueMatcher = this.infoValuePattern.matcher(infoBase);
            List<String> valueList = new ArrayList<String>(8);
            while (infoValueMatcher.find()) {
                valueList.add(infoValueMatcher.group(1));
            }
            //获取目标值
            String name;
            String value;
            for (int num = 0; num < labelList.size() && num < valueList.size(); num++) {
                name = labelList.get(num);
                if (name.length() > 0) {
                    name = this.attrNameMap.get(name);
                    if (name != null) {
                        resultMap.put(name, valueList.get(num));
                    }
                }
            }
            //抓取标签
            String tagValue;
            index = text.lastIndexOf("pl_profile_infoTag");
            if (index == -1) {
                tagValue = "";
            } else {
                String infoTag = text.substring(index);
                index = infoTag.indexOf("</script>");
                infoTag = infoTag.substring(0, index);
                infoTag = this.filter(infoTag);
                Matcher infoTagMatcher = this.infoTagPattern.matcher(infoTag);
                StringBuilder tagBuilder = new StringBuilder(256);
                while (infoTagMatcher.find()) {
                    value = infoTagMatcher.group(1);
                    tagBuilder.append(value.toLowerCase()).append(' ');
                }
                tagValue = tagBuilder.toString();
            }
            resultMap.put("tag", tagValue);
            //检测
            for (String attrName : this.attrNames) {
                if (resultMap.containsKey(attrName) == false) {
                    resultMap.put(attrName, "");
                }
            }
        }
        return resultMap;
    }

    @Override
    public String getFollowText(String sourceId) {
        String result = "";
        String path = this.replaceIdPattern.matcher(this.followPath).replaceFirst(sourceId);
        String url = this.rootUrl.concat(path);
        String response = this.get(url);
        int index = response.lastIndexOf("pl_relation_hisFollow");
        if (index == -1) {
            System.err.println(url);
            System.err.println("访问出现异常");
            System.err.println(response);
        } else {
            result = response;
        }
        return result;
    }

    @Override
    public List<String> parseFollowText(String text) {
        List<String> idList;
        int index = text.lastIndexOf("pl_relation_hisFollow");
        if (index == -1) {
            idList = new ArrayList<String>(0);
        } else {
            String followText = text.substring(index);
            index = followText.indexOf("</script>");
            followText = followText.substring(0, index);
            followText = this.filter(followText);
            Set<String> idSet = new HashSet<String>(32, 1);
            Matcher followMatcher = this.followPattern.matcher(followText);
            while (followMatcher.find()) {
                idSet.add(followMatcher.group(1));
            }
            idList = new ArrayList<String>(30);
            idList.addAll(idSet);
        }
        return idList;
    }

    @Override
    public void sendMessage(String id, String nickName, String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> getNewCookie(Map<String, String> cookieMap) {
        BrowserProxySessionBeanRemote remote = BrowserProxySessionBeanRemoteFactory.getBrowserProxySessionBeanRemote();
        return remote.getNewCookie(this.rootUrl, cookieMap);
    }

    @Override
    public Map<String, String> getLoginCookie(String userName, String password) {
        BrowserProxySessionBeanRemote remote = BrowserProxySessionBeanRemoteFactory.getBrowserProxySessionBeanRemote();
        return remote.getLoginCookie(this.rootUrl, userName, this.userNameXPath, password, this.passwordXPath, this.checkCodeXPath, this.checkCodeLength, this.loginBtnXPath);
    }
}
