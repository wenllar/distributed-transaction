package com.wenllar.interceptor;

import com.wenllar.holder.BatchTransactionServiceCountHolder;
import com.wenllar.holder.GroupIdHolder;
import com.wenllar.holder.TransactionCountHolder;
import com.wenllar.holder.constant.Constants;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoupIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
          String groupId = (String) request.getHeader(Constants.GROUP_ID);
          // 存在分支事务的服务数量
          String totalSubTxs = (String) request.getHeader(Constants.EXIST_BATCH_TRANSACTION_COUNT);
          if(StringUtils.hasText(groupId)){
              GroupIdHolder.set(groupId);
          }
          if(null != totalSubTxs && !"null".equalsIgnoreCase(totalSubTxs) && StringUtils.hasText(totalSubTxs)){
            Integer totalSubTransactions = Integer.valueOf(totalSubTxs) ;
            BatchTransactionServiceCountHolder.set(totalSubTransactions);
        }
        return true;
    }

}
