<template> 
    <div>
    <el-row></el-row>
        <el-row>       
            <el-col :offset="5" :span="10">    
                <div class="line_bottom"><h1>{{ $t("message.fundCreate.fund_create") }}</h1> </div>
                <div class="line_margin_top"></div>
            </el-col>     
        </el-row>
        <el-row>    
            <el-col :offset="7" :span="6">
                <el-form :label-position="right" :rules="rules" ref="fundProp" label-width="100px" :model="fundProp">
                    <el-form-item :label=" $t('message.fundCreate.fund_code') " prop="code">
                        <el-input v-model="fundProp.code"></el-input>
                    </el-form-item>
                    <el-form-item :label=" $t('message.fundCreate.fund_name') " prop="name">
                        <el-input v-model="fundProp.name"></el-input>
                    </el-form-item>
                    <el-form-item :label=" $t('message.fundCreate.fund_shortname') " prop="shortName">
                        <el-input v-model="fundProp.shortName"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button style="width:150px" type="success" @click="submitForm('fundProp')">{{ $t("message.fundCreate.fund_crt") }}</el-button>
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
                fundProp: {
                    code: '',
                    name: '',
                    shortName: ''
                },
                rules:{
                    code: [{ required: true, message: '', trigger: 'blur' }],
                    name: [{ required: true, message: '', trigger: 'blur' }],
                    shortName: [{ required: true, message: '', trigger: 'blur' }]
                }
            }
        },
        mounted: function(){
            this.rules.code[0].message=this.$t('message.fundCreate.fund_req_code');
            this.rules.name[0].message=this.$t('message.fundCreate.fund_req_name');
            this.rules.shortName[0].message=this.$t('message.fundCreate.fund_req_shortname');
        },
        methods: {
           submitForm(fundProp) {
                var _self = this;            
                _self.$refs[fundProp].validate((valid) => {
                    if (valid) {
                        _self.winsigns.ds.POST({url:_self.winsigns.api.fundURL.funds,
                                data:{
                                    code:_self.fundProp.code, 
                                    name:_self.fundProp.name,
                                    shortName:_self.fundProp.shortName}},
                        function(data){                                          
                            //创设成功
                            _self.$message({
                                message: _self.$t('message.fundCreate.fund_create_setsuccess'),
                                type: 'success'
                            });
                            _self.$router.push({ name: 'Fund', params: {}})
                        },function(data){
                            _self.$message.error(_self.$t('message.fundCreate.fund_create_error'));
                        }) 
                    } else {                        
                        return false;
                    }
                });
            }
        }
    }
</script>
<style scoped>
   
</style>