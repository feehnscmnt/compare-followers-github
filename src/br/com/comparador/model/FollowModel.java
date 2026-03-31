package br.com.comparador.model;

import com.google.gson.annotations.SerializedName;
import lombok.ToString;
import lombok.Data;

/**
 * Classe model para o tráfego dos dados obtidos da API do Github.
 * 
 * @author Felipe Nascimento
 *
 */

@Data
@ToString
public class FollowModel {
	private String login;
    private Integer id;
    
    @SerializedName("node_id")
    private String nodeId;
    
    @SerializedName("avatar_url")
    private String avatarUrl;
    
    @SerializedName("gravatar_id")
    private String gravatarId;
    
    private String url;
    
    @SerializedName("html_url")
    private String htmlUrl;
    
    @SerializedName("followers_url")
    private String followersUrl;
    
    @SerializedName("following_url")
    private String followingUrl;
    
    @SerializedName("gists_url")
    private String gistsUrl;
    
    @SerializedName("starred_url")
    private String starredUrl;
    
    @SerializedName("subscriptions_url")
    private String subscriptionsUrl;
    
    @SerializedName("organizations_url")
    private String organizationsUrl;
    
    @SerializedName("repos_url")
    private String reposUrl;
    
    @SerializedName("events_url")
    private String eventsUrl;
    
    @SerializedName("received_events_url")
    private String receivedEventsUrl;
    
    private String type;
    
    @SerializedName("user_view_type")
    private String userViewType;
    
    @SerializedName("site_admin")
    private Boolean siteAdmin;
}