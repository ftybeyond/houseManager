/*main.js*/
require.config({
    'baseUrl' : '/vendors',
    'paths' : {
        'jquery' : 'jquery/1.x/jquery.min',//只写文件名，不用带后缀
        'bootstrap' : 'bootstrap/js/bootstrap.min',
        'smartresize':'gentelella/js/smartresize',
        'main' : 'gentelella/js/custom',
        'select2':'select2/select2.min',
        'datatable':'datatable/dataTables.min',
        'dataTables-bs':'datatable/dataTables.bootstrap.min',
        'jquery-serializejson':'jquery/jquery.serializejson.min',
        'layer':'layer/layer',
        'laydate':'layer/laydate',
        'common':'../script/common',
        'select2-cn':'select2/zh-CN',
        'mySelect':'../script/commonSelect',
        'my97date':'My97DatePicker/WdatePicker',
        'moment':'moment/moment.min',
        'zTree':'zTree/js/jquery.ztree.all.min',
        'houseTree':'../script/houseTree'

    },
    'shim' : {
        'bootstrap' : {
            'deps' : ['jquery']
        },
        'smartresize':['jquery'],
        'main' : {
            'deps' : ['bootstrap','smartresize']
        },
        'layer':['jquery'],
        'select2':['jquery'],
        'select2-cn':['select2'],
        'mySelect':['select2','select2-cn'],
        'dataTables-bs':['bootstrap','datatable','layer','jquery-serializejson'],
        'zTree':['jquery'],
        'houseTree':['zTree']
    }
});