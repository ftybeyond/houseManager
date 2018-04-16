/*main.js*/
require.config({
    'baseUrl' : '/vendors',
    'paths' : {
        'jquery' : 'jquery/1.x/jquery.min',//只写文件名，不用带后缀
        'bootstrap' : 'bootstrap/js/bootstrap.min',
        'main' : 'gentelella/js/custom.min',
        'select2':'select2/select2.min',
        'datatable':'datatable/dataTables.min',
        'dataTables-bs':'datatable/dataTables.bootstrap.min',
        'jquery-serializejson':'jquery/jquery.serializejson.min',
        'layer':'layer/layer',
        'common':'../script/common'
    },
    'shim' : {
        'bootstrap' : {
            'deps' : ['jquery']
        },
        'main' : {
            'deps' : ['bootstrap']
        },
        'layer':['jquery'],
        'select2':['jquery'],
        'dataTables-bs':['bootstrap','datatable','layer','jquery-serializejson']
    }
});