<template>
    <div class="manageUser-wrapper">
        <a-tabs>

            <a-tab-pane tab="用户管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addUser"><a-icon type="plus" />添加用户</a-button>
                </div>
                <a-table
                        :columns="columns2"
                        :dataSource="userList"
                        bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="record">

                    <a-popconfirm
                            title="确定想删除该用户吗？"
                            @confirm="confirmDeleteUser(record.id)"
                            okText="确定"
                            cancelText="取消"
                    >
                        <a-button type="danger" size="small">删除用户</a-button>
                    </a-popconfirm>

                    <a-divider type="vertical"></a-divider>

                    <a-button type="primary" size="small" @click="addCredit(record)">增加信用值</a-button>

                    <a-divider type="vertical"></a-divider>

                    <a-button size="small" @click="updateUser(record)">修改信息</a-button>

                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="酒店管理员管理" key="2">
            <div style="width: 100%; text-align: right; margin:20px 0">
                <a-button type="primary" @click="addManager"><a-icon type="plus" />添加酒店管理员</a-button>
            </div>
            <a-table
                    :columns="columns1"
                    :dataSource="managerList"
                    bordered
            >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                <span slot="action" slot-scope="record">
                    <a-popconfirm
                            title="确定想删除该管理员吗？"
                            @confirm="confirmDeleteUser(record.id)"
                            okText="确定"
                            cancelText="取消"
                    >
                        <a-button type="danger" >删除管理员</a-button>
                    </a-popconfirm>
                    <a-divider type="vertical"></a-divider>
                    <a-button type="primary" @click="updateManager(record)">修改信息</a-button>
                    </span>

                <!--<span slot="action" slot-scope="record">-->
                <!--        <a-button type="primary" @click="order(record)">修改信息</a-button>-->
                <!--    </span>-->
            </a-table>
            </a-tab-pane>

        </a-tabs>
        <AddManagerModal></AddManagerModal>
        <AddUserModal></AddUserModal>
        <AddCreditModal></AddCreditModal>
        <UpdateManagerModal></UpdateManagerModal>
        <UpdateUserModal></UpdateUserModal>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddManagerModal from './components/addManagerModal'
import UpdateManagerModal from './components/updateManagerModal'
import UpdateUserModal from './components/updateUserModal'
import AddUserModal from './components/addUserModal'
import AddCreditModal from './components/addCreditModal'
const columns1 = [
    {  
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {  
        title: '用户名',
        dataIndex: 'userName',
    },
    {
        title: '用户密码',
        dataIndex: 'password',
    },
    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
        title: '生日',
        dataIndex: 'birthday',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const columns2 = [
    {
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {
        title: '用户名',
        dataIndex: 'userName',
    },
    {
        title: '用户密码',
        dataIndex: 'password',
    },
    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
        title: '信用值',
        dataIndex: 'credit',
    },
    {
        title: '生日',
        dataIndex: 'birthday',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
export default {
    name: 'manageUser',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns1,
            columns2,
            data: [],
            form: this.$form.createForm(this, { name: 'manageUser' }),
        }
    },
    components: {
        UpdateManagerModal,
        AddManagerModal,
        AddUserModal,
        AddCreditModal,
        UpdateUserModal,
    },
    computed: {
        ...mapGetters([
            'addManagerModalVisible',
            'updateManagerModalVisible',
            'updateUserModalVisible',
            'addUserModalVisible',
            'addCreditModalVisible',
            'managerList',
            'userList',
            'activeUserId',
            'beforeCredit'
        ])
    },
    mounted() {
      this.getUserList()
      this.getManagerList()
    },
    methods: {
        ...mapActions([
            'getManagerList',
            'getUserList',
            'deleteUser',
        ]),
        ...mapMutations([
            'set_addManagerModalVisible',
            'set_currentManager',
            'set_addUserModalVisible',
            'set_addCreditModalVisible',
            'set_updateManagerModalVisible',
            'set_updateUserModalVisible',
            'set_activeUserId',
            'set_currentUser',
            'set_beforeCredit',
        ]),
        addManager(){
            this.set_addManagerModalVisible(true)
        },
        addUser(){
            this.set_addUserModalVisible(true)
        },
        confirmDeleteUser(userId){
            this.deleteUser(userId)
        },
        addCredit(record){
            this.set_activeUserId(record.id)
            this.set_beforeCredit(record.credit)
            this.set_addCreditModalVisible(true)
        },
        updateManager(record){
            this.set_updateManagerModalVisible(true);
            this.set_currentManager(record);
        },
        updateUser(record){
            this.set_activeUserId(record.id);
            this.set_currentUser(record);
            this.set_updateUserModalVisible(true)
        }
    }
}
</script>
<style scoped lang="less">
    .manageUser-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>