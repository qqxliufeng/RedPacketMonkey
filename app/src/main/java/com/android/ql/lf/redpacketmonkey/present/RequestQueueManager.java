package com.android.ql.lf.redpacketmonkey.present;

import com.android.ql.lf.redpacketmonkey.component.ApiParams;

import java.util.LinkedList;
import java.util.List;

import okhttp3.MultipartBody;

public class RequestQueueManager {

    private LinkedList<RequestQueueBean> requestQueueList;

    private RequestQueueBean currentRequestQueueBean;


    public RequestQueueManager() {
        requestQueueList = new LinkedList<>();
    }

    private boolean isRequesting = false;

    public void startRequest(RequestQueueBean requestQueueBean) {
        requestQueueList.offer(requestQueueBean);
    }

    public void endRequest(GetDataFromNetPresent present) {
        requestQueueList.remove(currentRequestQueueBean);
        if (!requestQueueList.isEmpty()) {
            currentRequestQueueBean = requestQueueList.getFirst();
//            present.requestQueue(currentRequestQueueBean);
        } else {
            setRequesting(false);
        }
    }

    public RequestQueueBean getRequestBean() {
        return requestQueueList.getFirst();
    }

    public void setRequesting(boolean requesting) {
        isRequesting = requesting;
    }

    public boolean canRequest() {
        return !isRequesting;
    }

    public static final class RequestQueueBean {

        private int requestId;
        private ApiParams params;
        private List<MultipartBody.Part> partList;

        public RequestQueueBean(int requestId, ApiParams params, List<MultipartBody.Part> partList) {
            this.requestId = requestId;
            this.params = params;
            this.partList = partList;
        }

        public RequestQueueBean(int requestId, ApiParams params) {

            this.requestId = requestId;
            this.params = params;
        }

        public int getRequestId() {
            return requestId;
        }

        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }

        public ApiParams getParams() {
            return params;
        }

        public void setParams(ApiParams params) {
            this.params = params;
        }

        public List<MultipartBody.Part> getPartList() {
            return partList;
        }

        public void setPartList(List<MultipartBody.Part> partList) {
            this.partList = partList;
        }
    }

}
