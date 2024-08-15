package com.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GalleryResponse {

    private Response response;

    @Data
    public static class Response {
        private Header header;
        private Body body;

        @Data
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Data
        public static class Body {
            private Items items;

            @Data
            public static class Items {
                private List<Item> item;

                @Data
                public static class Item {
                    @JsonProperty("galContentId")
                    private String galContentId;
                    @JsonProperty("galContentTypeId")
                    private String galContentTypeId;
                    @JsonProperty("galTitle")
                    private String galTitle;
                    @JsonProperty("galWebImageUrl")
                    private String galWebImageUrl;
                    @JsonProperty("galCreatedtime")
                    private String galCreatedtime;
                    @JsonProperty("galModifiedtime")
                    private String galModifiedtime;
                    @JsonProperty("galPhotographyMonth")
                    private String galPhotographyMonth;
                    @JsonProperty("galPhotographyLocation")
                    private String galPhotographyLocation;
                    @JsonProperty("galPhotographer")
                    private String galPhotographer;
                    @JsonProperty("galSearchKeyword")
                    private String galSearchKeyword;
                }
            }
        }
    }
}
