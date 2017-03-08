<template>
  <div class="dialogs">
    <div class="dialog" v-bind:class="{ 'dialog-active': show }" @open="openDialog">
      <div class="dialog-content">
        <header class="dialog-header">
          <h1 class="dialog-title">{{ title }}</h1>
        </header>
        <div class="dialog-body">
          <div v-for="field in fields" class="form-group">
            <label>{{ field.name }}</label>
            <select v-if="field.dataSource" v-model="item[field.name]" :disabled="mode === 2 && field.isKey">
              <option v-for="opt in field.dataSource" :value="opt">{{ opt }}</option>
            </select>
            <input v-else type="text" v-model="item[field.name]" :disabled="mode === 2 && field.isKey">
          </div>
        </div>
        <footer class="dialog-footer">
          <div class="form-group">
            <label></label>
            <el-button type="primary" class="btn-save" v-on:click="save">保存</el-button>
            <el-button class="btn-close" v-on:click="close">关闭</el-button>
          </div>
        </footer>
      </div>
    </div>
    <div class="dialog-overlay"></div>
  </div>
</template>

<script>
   import bus from '../eventbus/bus'
   export default{
    data: function() {
      return {
        // 对话框默认是不显示的
        show: false
      }
    },
    /*
     * mode = 1是新增数据模式，mode = 2是修改数据模式
     * title表示对话框的标题内容
     * fields表示对话框要显示的数据字段数组
     * item是由调用者传下来，用于绑定表单字段的
     */
    props: ['mode', 'title', 'fields', 'item'],
    methods: {
      openDialog(){

      },
      close: function() {
        this.show = false
      },
      save: function() {
        if (this.mode === 1) {
            // 使用$dispatch调用simple-grid的create-item事件
            bus.$emit('create-item')
        } else if (this.mode === 2) {
            // 使用$dispatch调用simple-grid的update-item事件
            bus.$emit('update-item')
        }
      }
    },
    mounted :function () {
      var _self = this;
      bus.$on('showDialog', function(show){      
          _self.show = show;
      });
    }
  }
</script>
<style>
  .dialog {
      width: 480px;
      position: fixed;
      left: 50%;
      top: 2em;
      transform: translateX(-50%);
      z-index: 2000;
      visibility: hidden;
      backface-visibility: hidden;
      perspective: 1300px;
  }

  .dialog-active {
      visibility: visible;
  }

  .dialog-active .dialog-content {
      opacity: 1;
      transform: rotateY(0);
  }

  .dialog-active~.dialog-overlay {
      opacity: 1;
      visibility: visible;
  }

  .dialog-content {
      border-radius: 3px;
      background: #fff;
      overflow: hidden;
      box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
      transition: .5s ease-in-out;
      opacity: 0;
      transform-style: preserve-3d;
      transform: rotateY(-70deg);
  }

  .dialog-header {
      background: #0090d3;
      color: #fff;
  }

  .dialog-title {
      margin: 0;
      font-size: 2em;
      text-align: center;
      font-weight: 200;
      line-height: 2em;
  }

  .dialog-body {
      padding: 2em;
  }

  .dialog-footer {
      margin: 0 2em;
      padding: 1em 0;
      border-top: 1px solid rgba(0, 0, 0, 0.1);
  }

  .dialog-overlay {
      content: "";
      position: fixed;
      visibility: hidden;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      z-index: 1000;
      opacity: 0;
      background: rgba(0, 0, 0, 0.5);
      transition: all .6s;
  }
</style>
