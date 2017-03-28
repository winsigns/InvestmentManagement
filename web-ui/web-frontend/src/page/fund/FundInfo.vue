<template> 
    <div>
        <el-row>
            <div class="line_bottom"><h1>{{ $t("message.fundCreate.base_info") }}</h1> </div>
            <div class="line_margin_top"></div>
        </el-row>
        <el-row>        
            <el-col :xs="16" :sm="14" :md="12" :lg="8" :offset="3">                
                <el-form v-loading="loading" :label-position="right" :rules="rules" ref="fundDetail" label-width="100px" :model="fundDetail">
                    <el-form-item :label="$t('message.fundCreate.fund_code')" prop="code">
                        <el-input v-model="fundDetail.code"></el-input>
                    </el-form-item>
                    <el-form-item :label=" $t('message.fundCreate.fund_name') " prop="name">
                        <el-input v-model="fundDetail.name"></el-input>
                    </el-form-item>
                    <el-form-item :label=" $t('message.fundCreate.fund_shortname') " prop="shortName">
                        <el-input v-model="fundDetail.shortName"></el-input>
                    </el-form-item>
                    <el-form-item :label=" $t('message.fundCreate.fund_share') ">
                        <el-input v-model="fundDetail.totalShares" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('fundDetail')">{{ $t("message.system.save") }}</el-button>
                        <el-button @click="cancelForm()">{{ $t("message.system.cancel") }}</el-button>
                    </el-form-item>
                </el-form>   
            </el-col>	                     
        </el-row>             
    </div>
</template>
<script>
    export default{
        data(){
            return {
                loading:true,
                fundDetail:{
                    code: '',
                    name: '',
                    shortName: ''
                },
                rules:{
                    code: [
                        { required: true, message: '', trigger: 'blur' }
                    ],
                    name: [
                        { required: true, message: '', trigger: 'blur' }
                    ],
                    shortName: [
                        { required: true, message: '', trigger: 'blur' }
                    ]            
                }
            }
        },
        mounted: function(){
           var _self = this;
            _self.rules.code[0].message=_self.$t('message.fundCreate.fund_req_code');
            _self.rules.name[0].message=_self.$t('message.fundCreate.fund_req_name');
            _self.rules.shortName[0].message=_self.$t('message.fundCreate.fund_req_shortname');
            _self.winsigns.ds.GET({url:_self.winsigns.api.fundURL.funds+_self.$route.params.fundId,
                    data:{}},function(data){ 
                _self.loading = false;  
                _self.fundDetail = data;             
            })   
        },
        methods: {
           submitForm(fundProp) {
                var _self = this;            
                _self.$refs[fundProp].validate((valid) => {
                    if (valid) {
                        _self.winsigns.ds.PUT({url:_self.winsigns.api.fundURL.funds+'/'+_self.$route.params.fundId,
                                data:{
                                    code:_self.fundDetail.code, 
                                    name:_self.fundDetail.name,
                                    shortName:_self.fundDetail.shortName}},
                        function(data){                                          
                            //创设成功
                            _self.$message({
                                message: _self.$t('message.system.success'),
                                type: 'success'
                            });
                            _self.$router.push({ name: 'Fund', params: {}})
                        },function(data){
                            _self.$message.error(_self.$t('message.system.fail'));
                        }) 
                    } else {                        
                        return false;
                    }
                });
            },cancelForm(){
                this.$router.push({ name: 'Fund', params: {}})
            }
        }
    }
</script>
<style scoped>
   
</style>