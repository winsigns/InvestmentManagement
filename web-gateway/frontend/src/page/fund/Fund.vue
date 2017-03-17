<template> 
    <section>  
        <div>     
            <el-row>
                <el-col :span="2">&nbsp;</el-col>
                <el-col :span="20">      
                    <div class="line_bottom"><h1>{{ $t("message.fund.fd_title") }}</h1> </div>              
                </el-col>  
            </el-row>
            <el-row v-loading="loading">                
                <el-col :span="3">&nbsp;</el-col>	   
                <el-col :span="20">
                    <div class="line_margin_top">
                        <div v-for="item in funds" class="fund_one_block " @click="goFundDetail(item)">                                                     
                            <div class="line_bottom log1" style="height:40px;">
                                <span v-text="item.shortName" class="cls_fundName"></span>
                                <small v-text="item.code" class="cls_fundCode"></small>   
                            </div>    
                            <div style="margin-top:20px;">
                                <el-tag type="primary">{{$t("message.fund.fd_life_cycle")}}</el-tag>
                            </div>   
                            <div style="margin-top:20px;">
                                <el-tag type="primary">净值</el-tag>
                                <el-tag type="primary" style="margin-left:60px">累计净值</el-tag>
                            </div>                                                                              
                        </div>                                                           
                    </div>    
                </el-col> 
            </el-row>                             
        </div>
    </section>
   
</template>
<script>
    import api from '../../config/api.json'
    import ds from '../../common/ds'    
    export default{
        data(){
            return {
                funds: [],
                loading: true
            }
        },
        created: function(){                      
            var _self = this;  
            ds.GET({url:api.fundURL.funds,
                    data:{}},function(data){
                _self.funds = data._embedded.funds;  
                _self.loading = false;              
            })             
        },
        methods:{
            goFundDetail: function(item){
                this.$router.push({ name: 'FundDetail', params: { 
                    fundId: item.id}});                   
            }
        }
    }
</script>
<style scoped>   

   .fund_one_block{
       width:250px;
       height:150px;
       margin:0 10px 20px;       
       float:left; 
       display:inline-block;
       overflow:hidden;
       text-overflow:ellipsis;
       white-space:nowrap;
       padding:10px;       
       border: 1px solid #e3e3e3;
       border-radius: 4px;
       box-shadow:3px 3px 3px #E2E2E2;
   }   

   .cls_fundName{
       font-size:16px;
       width:110px;
       height:19px;
   }
   .cls_fundCode{
       width:80px;
       height:18px;
   }
</style>