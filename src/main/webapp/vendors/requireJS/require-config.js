/*main.js*/
require.config({
    'baseUrl' : '/vendors',
    'paths' : {
        'jquery' : 'jquery/1.x/jquery.min',//只写文件名，不用带后缀
        'bootstrap' : 'bootstrap/js/bootstrap.min',
        'smartresize':'gentelella/js/smartresize',
        'main' : 'gentelella/js/custom',
        'select2':'select2/select2',
        'datatable':'datatable/dataTables.min',
        'dataTables-bs':'datatable/dataTables.bootstrap.min',
        'datatables-buttons':'datatable/dataTables.buttons.min',
        'datatables-jszip':'datatable/jszip.min',
        'datatables-pdfmake':'datatable/pdfmake.min',
        'datatables-vfs_fonts':'datatable/vfs_fonts',
        'datatables-buttons-flash':'datatable/buttons.flash.min',
        'datatables-buttons-html5':'datatable/buttons.html5.min',
        'datatables-print':'datatable/buttons.print.min',
        'jquery-serializejson':'jquery/jquery.serializejson',
        'jquery-ajaxForm':'jquery/jquery.form.min',
        'jquery_validate':'jquery/jquery.validate.min',
        'jquery_validate_zh':'jquery/jquery.validate.zh',
        'layer':'layer/layer',
        'laydate':'layer/laydate',
        'common':'../script/common',
        'select2-cn':'select2/zh-CN',
        'mySelect':'../script/commonSelect',
        'my97date':'My97DatePicker/WdatePicker',
        'moment':'moment/moment.min',
        'zTree':'zTree/js/jquery.ztree.all',
        'houseTree':'../script/houseTree',
        'houseTreeSelect':'../script/houseTreeSelect'

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
        // 'datatables-print':['datatables-buttons','datatables-jszip','datatables-vfs_fonts','datatables-buttons-flash','datatables-buttons-html5']
    }
});