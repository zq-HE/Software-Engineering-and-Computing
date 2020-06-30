<template>
    <div>
        <a-modal
                :visible="InfoVisible"
                title="管理酒店基本信息"
                cancelText="取消"
                okText="确定"
                @cancel="cancel"
                @ok="handleSubmit"
        >
            <!-- 这里是模态框内容区域，请编写列表代码与添加策略按钮 -->
            <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
                <a-form-item label="酒店地址" v-bind="formItemLayout">
                    <a-input
                            type="textarea"
                            :rows="1"
                            :placeholder="currentHotel.address"
                            v-decorator="['address', { rules: [{ required: true,
message: '请输入酒店地址' }] }]"
                    />
                </a-form-item>
                <a-form-item label="所属商圈" v-bind="formItemLayout">
                    <a-select
                            :placeholder="currentHotel.bizRegion"
                            v-decorator="[
 'bizRegion',
 { rules: [{ required: true, message: '请选择商圈' }] }]">
                        <a-select-option value="XiDan">西单</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="简介" v-bind="formItemLayout">
                    <a-input
                            type="textarea"
                            :rows="2"
                            :placeholder="currentHotel.description"
                            v-decorator="['description', { rules: [{ required: true,
message: '请输入简介' }] }]"
                    />
                </a-form-item>
                <a-form-item label="星级" v-bind="formItemLayout">
                    <a-select
                            :placeholder="currentHotel.hotelStar"
                            v-decorator="[
 'hotelStar',
 { rules: [{ required: true, message: '请选择星级' }] }]">
                        <a-select-option value="Three">三星</a-select-option>
                        <a-select-option value="Four">四星</a-select-option>
                        <a-select-option value="Five">五星</a-select-option>
                    </a-select>
                </a-form-item>

            </a-form>

        </a-modal>

    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "Info",
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
        components: {},
        computed: {
            ...mapGetters([
                'InfoVisible',
                'currentHotel',
                'activeHotelId',
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'Info' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_InfoVisible',
            ]),
            ...mapActions([
                'getHotelInfo',
                'updateHotelInfo'
            ]),
            cancel() {
                this.set_InfoVisible(false)
            },
            handleSubmit(e){
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            // 这里添加接口参数
                            id:this.activeHotelId,
                            name:this.currentHotel.name,
                            address:this.form.getFieldValue('address'),
                            bizRegion: this.form.getFieldValue('bizRegion'),
                            description:this.form.getFieldValue('description'),
                            hotelStar:this.form.getFieldValue('hotelStar'),
                            rate:this.currentHotel.rate,
                            phoneNum:this.currentHotel.phoneNum,
                        }
                        this.updateHotelInfo(data)
                    }
                });
            }
        },
    }
</script>

<style scoped>

</style>