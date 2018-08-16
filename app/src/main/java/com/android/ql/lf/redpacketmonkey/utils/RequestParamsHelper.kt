package com.android.ql.lf.redpacketmonkey.utils

import com.android.ql.lf.redpacketmonkey.component.ApiParams
import com.android.ql.lf.redpacketmonkey.data.PostBankCardBean
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

        private fun getWithIdParams(mod: String, act: String): ApiParams {
            val params = getBaseParams(mod, act)
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

        fun getVerupdateParam() = getBaseParams(SYSTEM_MODEL, "verupdate")


        /**              system model  end           **/


        /**              login model  start           **/
        val LOGIN_MODEL = "login"
        val ACT_REGISTER = "loginDo"
        val ACT_LOGIN = "login"
        val ACT_FORGETPW = "loginUp"

        fun getRegisterParams(nickname: String, phone: String = "", pass: String = ""): ApiParams {
            val params = getBaseParams().addParam(ApiParams.MOD_NAME, LOGIN_MODEL).addParam(ApiParams.ACT_NAME, ACT_REGISTER)
            params.addParam("phone", phone).addParam("pass", pass).addParam("nickname", nickname)
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

        fun getIMgroupParam(userAs: String, uid: String) = getBaseParams()
                .addParam(ApiParams.MOD_NAME, LOGIN_MODEL)
                .addParam(ApiParams.ACT_NAME, "logingroup")
                .addParam("user_as", userAs)
                .addParam("uid", uid)

        fun getLoginRedParam() = getWithIdParams(LOGIN_MODEL,"loginRed")

        /**              login model  end           **/

        /**              user model start           **/

        val USER_MODEL = "user"


        val ACT_UPDATE_FACE = "pic"

        val ACT_PERSONAL = "info"
        fun getPersonalParam(uid: String) = getBaseParams().addParam(ApiParams.MOD_NAME, USER_MODEL).addParam(ApiParams.ACT_NAME, ACT_PERSONAL).addParam("uid", uid)

        fun getNickNameParam(nickname: String = "") = getWithIdParams()
                .addParam(ApiParams.MOD_NAME, USER_MODEL)
                .addParam(ApiParams.ACT_NAME, "nickname")
                .addParam("nickname", nickname)

        fun getSexParam(sex: String) = getWithIdParams().addParam(ApiParams.MOD_NAME, USER_MODEL)
                .addParam(ApiParams.ACT_NAME, "sex")
                .addParam("sex", sex)

        fun getAddressParam(address: String) = getWithIdParams().addParam(ApiParams.MOD_NAME, USER_MODEL)
                .addParam(ApiParams.ACT_NAME, "dizhi")
                .addParam("dizhi", address)

        fun getResetPasswordParam(ypass: String, xpass: String, rpass: String) = getWithIdParams(USER_MODEL, "password")
                .addParam("ypass", ypass)
                .addParam("xpass", xpass)
                .addParam("rpass", rpass)


        fun getAddBankCarParam(postBankCardBean: PostBankCardBean) = getWithIdParams(USER_MODEL, "cardAdd")
                .addParam("card_nickname", postBankCardBean.name)
                .addParam("card_number", postBankCardBean.cardNumber)
                .addParam("card_type", "身份证")
                .addParam("card_numbers", postBankCardBean.idCardNumber)
                .addParam("card_phone", postBankCardBean.phone)

        fun getRankParam(act: String) = getWithIdParams(USER_MODEL, act)

        fun getBankCarListParam() = getWithIdParams(USER_MODEL, "cardList")

        fun getPayPasswordParam(pass: String) = getWithIdParams(USER_MODEL, "pass").addParam("pass", pass)

        fun getSoundSwitchParam(type: Int, state: Int) = getWithIdParams(USER_MODEL, "isstate").addParam("type", type).addParam("state", state)

        /**              user model end           **/


        /**              money model start           **/

        val MONEY_MODEL = "money"

        fun getReChargeParam(money: String) = getWithIdParams(MONEY_MODEL, "recharge").addParam("money", money)

        fun getCrashParam(card: String, sum: String) = getWithIdParams(MONEY_MODEL, "deposit").addParam("card", card).addParam("sum", sum)

        /**              money model end           **/


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

        /**             log model end    **/

        val LOG_MODEL = "log"

        fun getRecordParam(type: Int, page: Int) = getWithPageParams(page).addParam(ApiParams.MOD_NAME, LOG_MODEL).addParam(ApiParams.ACT_NAME, "recharge").addParam("type", type)

        fun getCountLog(type: Int) = getWithIdParams(LOG_MODEL, "coulog").addParam("type", type)

        /**             log model start    **/


        /**             group model start    **/

        val GROUP_MODEL = "group"

        fun getGroupListParam(page: Int) = getWithPageParams(page).addParam(ApiParams.MOD_NAME, GROUP_MODEL).addParam(ApiParams.ACT_NAME, "group")

        fun getGroupInfoParam(groupId: String) = getWithIdParams().addParam(ApiParams.MOD_NAME, GROUP_MODEL).addParam(ApiParams.ACT_NAME, "groupInfo").addParam("group_id", groupId)

        fun getSendRedPacketParam(groupId: String, money: String, mine: String) = getWithIdParams(GROUP_MODEL, "groupqueue").addParam("group_id", groupId).addParam("money", money).addParam("mine", mine)

        fun getGroupSettingParam(groupId: String) = getWithIdParams(GROUP_MODEL, "groupUser").addParam("group_id", groupId)

        fun getGroupUserList(groupId: String) = getWithIdParams(GROUP_MODEL, "groupUserList").addParam("group_id", groupId)

        /**             group model end    **/


        /**             red packet model start    **/


        val RED_PACKET_MODEL = "red"

        fun getRedPacketClickParam(red_id: String) = getWithIdParams(RED_PACKET_MODEL, "clicks").addParam("red_id", red_id)

        fun getRedPacketListsParam(red_id: String) = getWithIdParams(RED_PACKET_MODEL, "lists").addParam("red_id", red_id)

        fun getRedBiddingParam(red_id: String) = getWithIdParams(RED_PACKET_MODEL, "bidding").addParam("red_id", red_id)

        fun getBackRedPacket(red_id: String) = getWithIdParams(RED_PACKET_MODEL, "refund").addParam("red_id", red_id)

        /**             red packet model end    **/

    }
}