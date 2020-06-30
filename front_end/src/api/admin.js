import { axios } from '@/utils/request'
const api = {
    adminPre: '/api/admin'
}
export function getManagerListAPI(){
    return axios({
        url: `${api.adminPre}/getAllManagers`,
        method: 'POST'
    })
}
export function getUserListAPI(){
    return axios({
        url: `${api.adminPre}/getAllUsers`,
        method: 'POST'
    })
}
export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}
export function addUserAPI(data) {
    return axios({
        url: `${api.adminPre}/addUser`,
        method: 'POST',
        data
    })
}
export function addCreditAPI(id,credit) {
    return axios({
        url: `${api.adminPre}/${id}/${credit}/addCredit`,
        method: 'GET',
    })
}
export function deleteUserAPI(userId) {
    return axios({
        url: `${api.adminPre}/${userId}/deleteUser`,
        method: 'GET',
    })
}
export function updateManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/${data.id}/userForm/updateManager`,
        method: 'POST',
        data
    })
}
export function updateUserAPI(data) {
    return axios({
        url: `${api.adminPre}/${data.id}/userForm/updateUser`,
        method: 'POST',
        data
    })
}