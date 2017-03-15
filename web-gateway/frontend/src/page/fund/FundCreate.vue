<template> 
    <div>
        <el-row>
            <el-col :span="2">&nbsp;</el-col>
            <el-col :span="20">
                <div class="line_bottom"><h1>创设基金产品</h1> </div>     
                <div class="line_margin_top"></div>
            </el-col>
            <el-col :span="2">&nbsp;</el-col>
        </el-row>
        <el-row>    
            <el-col :span="3">&nbsp;</el-col>	    
            <el-col :xs="16" :sm="14" :md="12" :lg="8">
                <el-form :label-position="right" :rules="rules" ref="fundProp" label-width="100px" :model="fundProp">
                    <el-form-item label="基金代码" prop="code">
                        <el-input v-model="fundProp.code"></el-input>
                    </el-form-item>
                    <el-form-item label="基金名称" prop="name">
                        <el-input v-model="fundProp.name"></el-input>
                    </el-form-item>
                    <el-form-item label="基金简称" prop="shortName">
                        <el-input v-model="fundProp.shortName"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button style="width:150px" type="success" @click="submitForm('fundProp')">创设</el-button>
                    </el-form-item>
                </el-form>   
            </el-col>	                     
        </el-row>             
    </div>
</template>
<script>
    import api from '../../config/api.json'
    import ds from '../../common/ds'
    export default{
        data(){
            return {
                fundProp: {
                    code: '',
                    name: '',
                    shortName: ''
                },
                rules:{
                    code: [
                        { required: true, message: '请输入基金代码', trigger: 'blur' }                    
                    ],
                    name: [
                        { required: true, message: '请输入基金名称', trigger: 'blur' }                    
                    ],
                    shortName: [
                        { required: true, message: '请输入基金简称', trigger: 'blur' }                    
                    ]            
                }
            }
        },
        methods: {
           submitForm(fundProp) {
                var _self = this;            
                _self.$refs[fundProp].validate((valid) => {
                    if (valid) {
                         ds.POST({url:api.fundURL.funds,
                                data:{
                                    code:_self.fundProp.code, 
                                    name:_self.fundProp.name,
                                    shortName:_self.fundProp.shortName}},
                        function(data){                                          
                            //创设成功
                            _self.$message({
                                message: _self.$t('message.fundCreate.fd_crt_setsuccess'),
                                type: 'success'
                            });
                            _self.$router.push({ name: 'Fund', params: {}})
                        },function(data){
                            _self.$message.error(_self.$t('message.fundCreate.fd_crt_error'));
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