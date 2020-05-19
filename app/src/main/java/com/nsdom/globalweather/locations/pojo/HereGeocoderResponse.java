package com.nsdom.globalweather.locations.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HereGeocoderResponse {

    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "HereGeocoderResponse{" +
                "response=" + response +
                '}';
    }

    public class Response {

        @SerializedName("view")
        @Expose
        private List<ViewResponse> view;

        public List<ViewResponse> getView() {
            return view;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "view=" + view +
                    '}';
        }

        public class ViewResponse {

            @SerializedName("result")
            @Expose
            private List<ResultResponse> result;

            public List<ResultResponse> getResult() {
                return result;
            }

            @Override
            public String toString() {
                return "ViewResponse{" +
                        "result=" + result +
                        '}';
            }
            public class ResultResponse {
                @SerializedName("location")
                @Expose
                private ResponseLocation location;

                public ResponseLocation getLocation() {
                    return location;
                }

                @Override
                public String toString() {
                    return "ViewResponse{" +
                            "location=" + location +
                            '}';
                }




                public class ResponseLocation {

                    @SerializedName("displayPosition")
                    @Expose
                    private ResponsePosition displayPosition;

                    public ResponsePosition getDisplayPosition() {
                        return displayPosition;
                    }

                    @Override
                    public String toString() {
                        return "ResponseLocation{" +
                                "displayPosition=" + displayPosition +
                                '}';
                    }
                }
            }
        }
    }
}
