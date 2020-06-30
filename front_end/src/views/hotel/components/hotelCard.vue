<template>
  <a-card v-if="showAll||(hasBookedVisible&&hotel.hasBooked==='曾预订')||(hasExecedVisible&&hotel.hasExeced==='曾执行')||(hasCanceledVisible&&hotel.hasCanceled==='曾撤销')" hoverable class="hotelCard ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
    <img
            alt="example"
            src="http://cos.nju.world:9000/public-pictures/NJU_北大楼.jpg"
            slot="cover"
            referrerPolicy="no-referrer"
    />
    <a-tooltip :title="hotel.title" placement="top">
      <a-card-meta :title="hotel.name">
        <template slot="description">
          <a-rate style="font-size: 15px" :value="this.starStrToNum(hotel.hotelStar)" disabled allowHalf/> <p></p> 酒店评分：{{hotel.rate|numFilter}}分
        </template>
        <!--
        <template slot="description">
          <p v-if="hasBookedVisible">预订状态：{{hotel.hasBooked}}</p>
          <p v-if="hasCanceledVisible">撤销状态：{{hotel.hasCanceled}}</p>
          <p v-if="hasExecedVisible">执行状态：{{hotel.hasExeced}}</p>
        </template>
        -->
      </a-card-meta>
    </a-tooltip>
  </a-card>
</template>
<script>
  import hotel from "../../../store/modules/hotel";

  export default {
    name:'',
    props: {
      hotel: {},
      showAll: {
        type:Boolean,
        default(){
          return true
        }
      },
      hasBookedVisible: {
        type:Boolean,
        default (){
          return true
        }
      },
      hasCanceledVisible: {
        type: Boolean,
        default() {
          return true;
        }
      },
      hasExecedVisible: {
        type: Boolean,
        default(){
          return true;
        }
      }
    },
    data() {
      return{
        hotelStarNum: 1
        // hasBookedVisible : true,
        // hasCanceledVisible : true,
        // hasExecedVisible : true
      }
    },
    watch:{
      showAll:function(n, o){
        this.showAll = n
      },
      hasBookedVisible:function (n,o) {
        // console.info("111111")
        this.hasBookedVisible = n
      },
      hasCanceledVisible: function (n, o) {
        this.hasCanceledVisible = n
      },
      hasExecedVisible: function (n, o) {
        this.hasExecedVisible = n
      }
    },
    methods: {
      starStrToNum(v){
        if(v==="One"){
          return 1
        }else if(v==="Two"){
          return 2
        }else if(v==="Three"){
          return 3
        }else if(v==="Four"){
          return 4
        }else if(v==="Five"){
          return 5
        }
        return 2
      }
    },
    filters: {
      numFilter (value) {
        let realVal = ''
        if (!isNaN(value) && value!== '') {
          // 截取当前数据到小数点后两位
          realVal = parseFloat(value).toFixed(2)
        } else {
          realVal = '--'
        }
        return realVal
      }
    }
  }
</script>
<style scoped lang="less">
  .hotelCard {
    margin: 10px 10px;
    min-width: 180px;
    max-height: 360px;
    img {
      height: 250px;
    }
  }
</style>

<style lang="less">
  .hotelCard{
    .ant-card-body{
      padding: 12px
    }
  }

  .ant-card-hoverable:hover {
    box-shadow: 0 2px 8px rgba(0,0,0,0.4)
  }

</style>