import { axios } from '@/utils/request'
const api = {
    couponPre: '/api/coupon'
}
export function hotelTargetMoneyAPI(data) {
    return axios({
        url: `${api.couponPre}/hotelTargetMoney`,
        method: 'POST',
        data,
    })
}

export function hotelTargetRoomAPI(data) {
    return axios({
        url: `${api.couponPre}/hotelTargetRoom`,
        method: 'POST',
        data,

    })
}

export function hotelTimeAPI(data) {
    return axios({
        url: `${api.couponPre}/hotelTime`,
        method: 'POST',
        data,
    })
}

export function hotelBirthdayAPI(data) {
    return axios({
        url: `${api.couponPre}/hotelBirthday`,
        method: 'POST',
        data,
    })
}

export function hotelAllCouponsAPI(hotelId) {
    return axios({
        url: `${api.couponPre}/hotelAllCoupons`,
        method: 'GET',
        params: {hotelId: hotelId},
    })
}

export function deleteCouponAPI(params) {
    return axios({
        url: `${api.couponPre}/deleteCoupon`,
        method: 'GET',
        params,
    })
}

export function orderMatchCouponsAPI(params) {
    return axios({
        url: `${api.couponPre}/orderMatchCoupons`,
        method: 'GET',
        params,
    })
}