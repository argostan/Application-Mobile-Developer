package com.example.FirmaAppSocial;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("albumId")
    @Expose
    private Integer albumId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("thumbnailUrl")
    @Expose
    private String thumbnailUrl;
    @SerializedName("data_pubblicazione")
    @Expose
    private String data_pubblicazione;

    @SerializedName("descrizione")
    @Expose
    private String descrizione;

    @SerializedName("tags")
    @Expose
    private String tags;

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getData_pubblicazione() {
        return data_pubblicazione;
    }

    public void setData_pubblicazione(String data_pubblicazione) {
        this.data_pubblicazione = data_pubblicazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}
