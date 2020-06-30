import {
    getManagerListAPI,
    getUserListAPI,
    addManagerAPI,
    updateManagerAPI,
    addUserAPI,
    addCreditAPI,
    deleteUserAPI,
    updateUserAPI,
} from '@/api/admin'
import { message } from 'ant-design-vue'

const admin = {
    state: {
        managerList: [

        ],
        userList: [

        ],
        addManagerModalVisible: false,
        updateManagerModalVisible: false,
        updateUserModalVisible: false,
        currentManager:{},
        currentUser:{},
        addUserModalVisible: false,
        addCreditModalVisible:false,
        updateManagerParams:{
            id:'',
            email:'',
            password:'',
            username: '',
            phonenumber: '',
            birthday:'',
        },
        addManagerParams: {
            email:'',
            password:'',
            username: '',
            phonenumber: '',
            birthday:'',
        },
        addUserParams: {
            email:'',
            password:'',
            username:'',
            phonenumber: '',
            birthday:'',
            credit:''
        },
        updateUserParams: {
            id:'',
            email:'',
            password:'',
            username:'',
            phonenumber: '',
            birthday:'',
        },
        addCreditParams:{
            credit: ''
        },
        activeUserId:0,
        beforeCredit:1000
    },
    mutations: {
        set_activeUserId: function(state, data) {
            state.activeUserId = data
        },
        set_beforeCredit: function(state, data) {
            state.beforeCredit = data
        },
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_userList: function(state, data) {
            state.userList = data
        },
        set_addManagerModalVisible: function(state, data) {
            state.addManagerModalVisible = data
        },
        set_updateManagerModalVisible: function(state,data){
            state.updateManagerModalVisible=data;
        },
        set_updateUserModalVisible: function(state,data){
            state.updateUserModalVisible=data;
        },
        set_currentManager: function(state,data){
            state.currentManager=data;
        },
        set_currentUser: function(state,data){
            state.currentUser=data;
        },
        set_addUserModalVisible: function(state, data) {
            state.addUserModalVisible = data
        },
        set_addCreditModalVisible: function(state, data) {
            state.addCreditModalVisible = data
        },
        set_addManagerParams: function(state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            }
        },
        set_updateManagerParams: function(state,data){
            state.updateManagerParams={
                ...state.updateManagerParams,
                ...data,
            }
        },
        set_updateUserParams: function(state,data){
            state.updateUserParams={
                ...state.updateUserParams,
                ...data,
            }
        },
        set_addUserParams: function(state, data) {
            state.addUserParams = {
                ...state.addUserParams,
                ...data,
            }
        },
        set_addCreditParams: function(state, data) {
            state.addCreditParams = {
                ...state.addCreditParams,
                ...data,
            }
        },
    },
    actions: {
        getManagerList: async({ commit }) => {
            const res = await getManagerListAPI()
            if(res){
                for(var i=0;i<res.length;i++){
                    res[i].birthday=res[i].birthday.substr(0,10)
                }
                commit('set_managerList', res)
            }
        },
        getUserList: async({ commit }) => {
            const res = await getUserListAPI()
            if(res){
                for(var i=0;i<res.length;i++){
                    res[i].birthday=res[i].birthday.substr(0,10)
                }
                commit('set_userList', res)
            }
        },
        addManager: async({ state, commit, dispatch }) => {
            const res = await addManagerAPI(state.addManagerParams)
            if(res) {
                commit('set_addManagerParams',{
                    id:'',
                    email:'',
                    password:'',
                    username:'',
                    phonenumber: '',
                    birthday:'',
                })
                commit('set_addManagerModalVisible', false)
                message.success('添加成功')
                dispatch('getManagerList')
            }else{
                message.error('添加失败')
            }
        },
        updateManager: async({ state, commit, dispatch }) => {
            const res = await updateManagerAPI(state.updateManagerParams)
            if(res) {
                commit('set_updateManagerParams',{
                    email:'',
                    password:'',
                    username: '',
                    phonenumber: '',
                })
                commit('set_updateManagerModalVisible', false)
                message.success('更新成功')
                dispatch('getManagerList')
            }else{
                message.error('更新失败')
            }
        },
        addUser: async({ state, commit, dispatch }) => {
            const res = await addUserAPI(state.addUserParams)
            if(res) {
                commit('set_addUserParams',{
                    email:'',
                    password:'',
                    username:'',
                    phonenumber: '',
                    birthday:'',
                    credit:''
                })
                commit('set_addUserModalVisible', false)
                message.success('添加成功')
                dispatch('getUserList')
            }else{
                message.error('添加失败')
            }
        },
        addCredit: async({ state, commit, dispatch },data) => {
            const res = await addCreditAPI(data.id,data.credit)
            if(res) {
                commit('set_addCreditParams',{
                    credit:''
                })
                commit('set_addCreditModalVisible', false)
                message.success('添加成功')
                dispatch('getUserList')
            }else{
                message.error('添加失败')
            }
        },
        updateUser: async({ state, commit, dispatch }) => {
            const res = await updateUserAPI(state.updateUserParams)
            if(res) {
                commit('set_updateManagerParams',{
                    id:'',
                    email:'',
                    password:'',
                    username:'',
                    phonenumber: '',
                    birthday:''
                })
                commit('set_updateUserModalVisible', false)
                message.success('更新成功')
                dispatch('getUserList')
            }else{
                message.error('更新失败')
            }
        },
        deleteUser: async({ state, dispatch }, userId) => {
            const res = await deleteUserAPI(userId)
            if(res) {
                dispatch('getUserList')
                dispatch('getManagerList')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        }
    }
}
export default admin