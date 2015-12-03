package com.tianzh.admin.business.analysis.service;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by pig on 2015-06-06.
 */
public interface HttpRequestService {

    String get(String url,HashMap<String,String> params) throws IOException;

    String get(String url) throws IOException;

    String post(String url,HashMap<String,String> params) throws Exception;

}
