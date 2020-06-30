import {
    addRoomAPI,
    addHotelAPI,
} from '@/api/hotelManager'
import {
    deleteHotelAPI,
    getHotelByIdAPI,
    updateHotelAPI
} from '@/api/hotel'
import {
    getAllOrdersAPI,
    deleteOrderAPI,
} from '@/api/order'
import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
    hotelTargetRoomAPI,
    hotelTimeAPI,
    hotelBirthdayAPI,
} from '@/api/coupon'
import { message } from 'ant-design-vue'
import {doOrderAPI} from "../../api/order";
import {deleteCouponAPI} from "../../api/coupon";

const hotelManager = {
    state: {
        orderList: [],
        addHotelParams: {
            name: '',
            address: '',
            bizRegion:'XiDan',
            hotelStar:'',
            rate: 0,
            description:'',
            phoneNum:'',
            managerId:'',
        },
        addHotelModalVisible: false,
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
        },
        addRoomModalVisible: false,
        couponVisible: false,
        addCouponVisible: false,
        activeHotelId: 0,
        couponList: [],
        InfoVisible:false,
        deleteCouponVisible : false,
        currentHotel:{}
    },
    mutations: {
        set_orderList: function(state, data) {
            state.orderList = data
        },
        set_addHotelModalVisible: function(state, data) {
            state.addHotelModalVisible = data
        },
        set_addHotelParams: function(state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            }
        },
        set_addRoomModalVisible: function(state, data) {
            state.addRoomModalVisible = data
        },
        set_addRoomParams: function(state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_couponVisible: function(state, data) {
            state.couponVisible = data
        },
        set_InfoVisible:function(state,data){
            state.InfoVisible=data
        },
        set_currentHotel:function(state,data){
            state.currentHotel=data
        },
        set_activeHotelId: function(state, data) {
            state.activeHotelId = data
        },
        set_couponList: function(state, data) {

            for(var i=0;i<data.length;i++){
                if(data[i].couponType===1){
                    data[i].couponType='生日特惠'
                }
                else if(data[i].couponType===2){
                    data[i].couponType='多间特惠'
                }
                else if(data[i].couponType===3){
                    data[i].couponType='满减特惠'
                }
                else if(data[i].couponType===4){
                    data[i].couponType='限时特惠'
                }
            }
            state.couponList = data
        },
        set_addCouponVisible: function(state, data) {
            state.addCouponVisible =data
        },
        set_deleteCouponVisible: function (state, data) {
            state.deleteCouponVisible = data
        }
    },
    actions: {
        getAllOrders: async({ state, commit }) => {
            const res = await getAllOrdersAPI()
            if(res){
                for(var i=0;i<res.length;i++){
                    res[i].checkInDate=res[i].checkInDate.substr(0,10)
                    res[i].checkOutDate=res[i].checkOutDate.substr(0,10)
                }
                commit('set_orderList', res)
            }
        },
        addHotel: async({ state, dispatch, commit }) => {
            const res = await addHotelAPI(state.addHotelParams)
            if(res){
                dispatch('getHotelList')
                commit('set_addHotelParams', {
                    name: '',
                    address: '',
                    bizRegion:'XiDan',
                    hotelStar:'',
                    rate: 0,
                    description:'',
                    phoneNum:'',
                    managerId:'',
                })
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        addRoom: async({ state, dispatch, commit }) => {
            const res = await addRoomAPI(state.addRoomParams)
            if(res){
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                })
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        deleteOrder: async({ state, dispatch }, orderId) => {
            const res = await deleteOrderAPI(orderId)
            if(res) {
                dispatch('getAllOrders')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },
        deleteHotel: async({ state, dispatch }, hotelId) => {
            const res = await deleteHotelAPI(hotelId)
            if(res) {
                dispatch('getHotelList')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },
        getHotelCoupon: async({ state, commit }) => {
            const res = await hotelAllCouponsAPI(state.activeHotelId)
            if(res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_couponList', res)
            }else {
                message.error('获取策略失败')
            }
        },
        getHotelInfo: async({ state, commit },id) => {
            const res = await getHotelByIdAPI({hotelId:id})
            if(res) {
                commit('set_currentHotel', res)
            }else {
                message.error('获取酒店信息失败')
            }
        },
        updateHotelInfo:async ({state,commit,dispatch},data)=>{
            if(data.address.length>255||data.description.length>255){
                message.error('更新失败,地址或者酒店简介过长')
            }
            else {
                const res = await updateHotelAPI(data)

                if (res) {
                    message.success('更新成功')
                    commit('set_InfoVisible', false)
                    dispatch('getHotelList')
                } else {
                    message.error('更新失败')
                }
            }
        },
        doOrder: async({ state, dispatch }, orderId) => {
            const res = await doOrderAPI(orderId)
            if(res) {
                dispatch('getAllOrders')
                message.success('执行成功')
            }else{
                message.error('执行失败')
            }
        },
        deleteHotelCoupon: async ({commit, dispatch}, data) => {
            const res = await deleteCouponAPI(data)
            if(res) {
                dispatch('getHotelCoupon')
                commit('set_deleteCouponVisible', false)
                commit('set_couponVisible',true)
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },
        addHotelCoupon: async({ commit, dispatch }, data) => {
            if(data.type===3){
                const res = await hotelTargetMoneyAPI(data)
                if(res){
                    // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                    dispatch('getHotelCoupon')
                    commit('set_addCouponVisible', false)
                    commit('set_couponVisible',true)
                    message.success('添加策略成功')
                }else{
                    // 添加失败后的操作
                    message.error('添加策略失败')
                }
            }
            else if(data.type===2){
                const res = await hotelTargetRoomAPI(data)
                if(res){
                    // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                    dispatch('getHotelCoupon')
                    commit('set_addCouponVisible', false)
                    commit('set_couponVisible',true)
                    message.success('添加策略成功')
                }else{
                    // 添加失败后的操作
                    message.error('添加策略失败')
                }
            }
            else if(data.type===4){
                const res = await hotelTimeAPI(data)
                if(res){
                    // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                    dispatch('getHotelCoupon')
                    commit('set_addCouponVisible', false)
                    commit('set_couponVisible',true)
                    message.success('添加策略成功')
                }else{
                    // 添加失败后的操作
                    message.error('添加策略失败')
                }
            }
            else{
                const res = await hotelBirthdayAPI(data)
                if(res){
                    // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                    dispatch('getHotelCoupon')
                    commit('set_addCouponVisible', false)
                    commit('set_couponVisible',true)
                    message.success('添加策略成功')
                }else{
                    // 添加失败后的操作
                    message.error('添加策略失败')
                }
            }
        }
    }
}
export default hotelManager