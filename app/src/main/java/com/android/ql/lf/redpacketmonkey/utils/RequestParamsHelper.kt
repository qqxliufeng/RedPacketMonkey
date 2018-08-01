package com.android.ql.lf.redpacketmonkey.utils

import com.android.ql.lf.redpacketmonkey.component.ApiParams
import com.android.ql.lf.redpacketmonkey.data.UserInfo


/**
 * Created by lf on 2017/11/13 0013.
 * @author lf on 2017/11/13 0013
 */
class RequestParamsHelper {

    companion object {

        private fun getBaseParams(): ApiParams {
            val params = ApiParams()
            params.addParam("pt", "android")
            return params
        }

        private fun getBaseParams(mod: String, act: String): ApiParams {
            val params = ApiParams()
            params.addParam(ApiParams.MOD_NAME, mod).addParam(ApiParams.ACT_NAME, act)
            params.addParam("pt", "android")
            return params
        }

        private fun getWithIdParams(): ApiParams {
            val params = getBaseParams()
            params.addParam("uid", UserInfo.getInstance().user_id)
            return params
        }

        private fun getWithPageParams(page: Int, pageSize: Int = 10): ApiParams {
            val param = if (UserInfo.getInstance().isLogin) {
                getWithIdParams()
            } else {
                getBaseParams()
            }
            param.addParam("page", page)
            param.addParam("pagesize", pageSize)
            return param
        }


        /**              system model  start           **/
        val SYSTEM_MODEL = "system"
        val ACT_PHONE = "phone"

        fun getPhoneParam(phone: String) = getBaseParams(SYSTEM_MODEL, ACT_PHONE)
                .addParam("phone", phone)


        /**              system model  end           **/


        /**              login model  start           **/
        val LOGIN_MODEL = "login"
        val ACT_REGISTER = "loginDo"
        val ACT_LOGIN = "login"
        val ACT_FORGETPW = "loginUp"

        fun getRegisterParams(nickname:String,phone: String = "", pass: String = ""): ApiParams {
            val params = getBaseParams().addParam(ApiParams.MOD_NAME, LOGIN_MODEL).addParam(ApiParams.ACT_NAME, ACT_REGISTER)
            params.addParam("phone", phone).addParam("pass", pass).addParam("nickname",nickname)
            return params
        }

        fun getLoginParams(phone: String = "", pass: String = ""): ApiParams {
            val params = getBaseParams().addParam(ApiParams.MOD_NAME, LOGIN_MODEL).addParam(ApiParams.ACT_NAME, ACT_LOGIN)
            params.addParam("phone", phone).addParam("pass", pass)
            return params
        }

        fun getUserProtocolParam(type: String = "1") = getBaseParams(LOGIN_MODEL, "deal").addParam("type", type)


        fun getForgetPWParams(phone: String = "", pass: String = "", repass: String = ""): ApiParams {
            val params = getBaseParams().addParam(ApiParams.MOD_NAME, LOGIN_MODEL).addParam(ApiParams.ACT_NAME, ACT_FORGETPW)
            params.addParam("phone", phone).addParam("pass", pass).addParam("repass", repass)
            return params
        }



        /**              login model  end           **/

        /**              user model start           **/

        val USER_MODEL = "user"

        val ACT_PERSONAL = "info"
        fun getPersonalParam(uid: String) = getBaseParams().addParam(ApiParams.MOD_NAME, USER_MODEL).addParam(ApiParams.ACT_NAME, ACT_PERSONAL).addParam("uid", uid)

        fun getNickNameParam(nickname:String = "") = getWithIdParams()
                .addParam(ApiParams.MOD_NAME, USER_MODEL)
                .addParam(ApiParams.ACT_NAME, "nickname")
                .addParam("nickname",nickname)

        /**              user model start           **/

        /**             area model start    **/

        val AREA_MODEL = "area"

        val ACT_PROVINCE = "province"
        val ACT_CITY = "city"
        val ACT_PROVINCE_CITY_AREA = "district"


        fun getProvinceParam() = getBaseParams()

        fun getCityParam(pid: String): ApiParams {
            val param = getBaseParams()
            param.addParam("pid", pid)
            return param
        }

        fun getProvinceCityAreaParam(cid: String): ApiParams {
            val param = getBaseParams()
            param.addParam("cid", cid)
            return param
        }

        /**             area model end    **/
    }
}