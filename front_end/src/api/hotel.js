import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function getHotelsAPI(userId) {
    return axios({
        url: `${api.hotelPre}/${userId}/all`,
        method: 'get',
    })
}
export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'GET',
    })
}
export function deleteHotelAPI(hotelId) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/deleteHotel`,
        method: 'GET',
    })
}
export function updateHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/updateHotel`,
        method: 'POST',
        data
    })
}
