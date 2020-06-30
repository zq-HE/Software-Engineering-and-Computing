import Vue from 'vue'
import router from '@/router'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { message } from 'ant-design-vue'
import {
    loginAPI,
    registerAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
    getCreditRecordAPI
} from '@/api/user'

import {
    getUserOrdersAPI,
    cancelOrderAPI,
    scoreOrderAPI
} from '@/api/order'
import {registerVipAPI} from "../../api/user";

const getDefaultState = () => {
    return {
        userId: '',
        userInfo: {

        },
        userOrderList: [

        ],
        creditRecord:[]
    }
}

const user = {
    state : getDefaultState(),

    mutations: {
        reset_state: function(state) {
            state.token = '',
            state.userId = '',
            state.userInfo = {
                
            },
            state.userOrderList = [],
            state.creditRecord=[]
        },
        set_token: function(state, token){
            state.token = token
        },
        set_email: (state, data) => {
            state.email = data
        },
        set_userId: (state, data) => {
            state.userId = data
        },
        set_userInfo: (state, data) => {
            state.userInfo = {
                ...state.userInfo,
                ...data
            }
        },
        set_userOrderList: (state, data) => {
            state.userOrderList = data
        },
        set_creditRecord:(state,data)=>{
            state.creditRecord=data
        }
    },

    actions: {
        login: async ({ dispatch, commit }, userData) => {
            const res = await loginAPI(userData)
            if(res){
                setToken(res.id)
                commit('set_userId', res.id)
                dispatch('getUserInfo')
                router.push('/hotel/hotelList')
            }
        },
        register: async({ commit }, data) => {
            const res = await registerAPI(data)
            if(res){
                message.success('注册成功')
            }
        },
        getUserInfo({ state, commit }) {
            return new Promise((resolve, reject) => {
              getUserInfoAPI(state.userId).then(response => {
                const data = response
                if (!data) {
                  reject('登录已过期，请重新登录')
                }
                commit('set_userInfo', data)
                commit('set_userId', data.id)
                resolve(data)
              }).catch(error => {
                reject(error)
              })
            })
        },
        getCreditRecord: async({ state, commit }) => {
            const data = {
                userId: Number(state.userId)
            }
            const res = await getCreditRecordAPI(data)
            if(res){
                for(var i=0;i<res.length;i++){
                    res[i].changeTime=res[i].changeTime.substr(0,10)
                }
                commit('set_creditRecord', res)
                console.log(state.creditRecord)
            }
        },
        updateUserInfo: async({ state, dispatch }, data) => {
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await updateUserInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        getUserOrders: async({ state, commit }) => {
            const data = {
                userId: Number(state.userId)
            }
            const res = await getUserOrdersAPI(data)
            if(res){
                for(var i=0;i<res.length;i++){
                    res[i].checkInDate=res[i].checkInDate.substr(0,10)
                    res[i].checkOutDate=res[i].checkOutDate.substr(0,10)
                }
                commit('set_userOrderList', res)
                console.log(state.userOrderList)
            }
        },
        cancelOrder: async({ state, dispatch }, orderId) => {
            const res = await cancelOrderAPI(orderId)
            if(res) {
                dispatch('getUserOrders')
                dispatch('getUserInfo')
                message.success('撤销成功')
            }else{
                message.error('撤销失败')
            }
        },
        scoreOrder:async({state,dispatch},info)=>{

            const res = await scoreOrderAPI({
                value:info.value,
                orderId:info.orderId
            })
            if(res) {
                dispatch('getUserOrders')
                message.success('评分成功')
            }else{
                message.error('评分失败')
            }
        },
        registerVip: async({ state, dispatch }, data) => {
            const params = {
                id: state.userId,
                ...data
            }
            const res = await registerVipAPI(params)
            if(res) {
                message.success('注册会员成功')
                dispatch('getUserInfo')
            }else{
                message.error('注册会员失败！')
            }
        },
        logout: async({ commit }) => {
            removeToken()
            resetRouter()
            commit('reset_state')
        },
          // remove token
        resetToken({ commit }) {
            return new Promise(resolve => {
                removeToken() // must remove  token  first
                commit('reset_state')
                resolve()
            })
        },
    }
}

export default user