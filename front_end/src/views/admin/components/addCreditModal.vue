<template>
    <a-modal
            :visible="addCreditModalVisible"
            title="添加信用值"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-Form :form="form">
            <a-form-item v-bind="formItemLayout" label="添加信用值">
                <a-input
                        v-decorator="[
                    'credit',
                    { rules: [{required: true, message: '请添加信用值', }] }
                ]"
                />
            </a-form-item>
        </a-Form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import {message} from "ant-design-vue";
    export default {
        name: 'addCreditModal',
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: { span: 12 },
                        sm: { span: 6 },
                    },
                    wrapperCol: {
                        xs: { span: 24 },
                        sm: { span: 16 },
                    },
                },
            }
        },
        computed: {
            ...mapGetters([
                'addCreditModalVisible',
                'userList',
                'activeUserId',
                'beforeCredit'
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'addCreditModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addCreditModalVisible',
                'set_addCreditParams',
            ]),
            ...mapActions([
                'getUserList',
                'addCredit',
            ]),
            cancel() {
                this.set_addCreditModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            id:this.activeUserId,
                            credit: this.form.getFieldValue('credit'),
                        }
                        if(Number(this.form.getFieldValue('credit'))<0){
                            message.error('添加的信用值必须为正')
                        }
                        else{
                            this.set_addCreditParams(data)
                            this.addCredit(data)
                        }
                    }
                });
            },
        }
    }
</script>