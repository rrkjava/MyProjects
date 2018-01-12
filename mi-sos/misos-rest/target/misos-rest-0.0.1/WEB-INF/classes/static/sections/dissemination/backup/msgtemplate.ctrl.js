'use strict';
 
angular.module('app.core').controller('MessageTemplateController', ['$scope', 'TemplateService', function($scope, TemplateService) {
    var self = this;
    self.template={id:null,messageTemplate:'hi'};
    self.msgtemplates=[];
 
    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;
 
 
    fetchAllTemplate();
 
    function fetchAllTemplate(){
        TemplateService.fetchAllTemplate()
            .then(
            function(response) {
                self.msgtemplates = response;
            },
            function(errResponse){
                console.error('Error while fetching templates');
            }
        );
    }
 
    function createTemplate(template){
        TemplateService.createTemplate(template)
            .then(
            fetchAllTemplate,
            function(errResponse){
                console.error('Error while creating template');
            }
        );
    }
 
    function updateTemplate(template, id){
        TemplateService.updateTemplate(template, id)
            .then(
            fetchAllTemplate,
            function(errResponse){
                console.error('Error while updating template');
            }
        );
    }
 
    function deleteTemplate(id){
        TemplateService.deleteTemplate(id)
            .then(
            fetchAllTemplate,
            function(errResponse){
                console.error('Error while deleting template');
            }
        );
    }
 
    function submit() {
        alert("submit");
        if(self.template.id===null){
            console.log('Saving New Template', self.template);
            createTemplate(self.template);
        }else{
            updateTemplate(self.template, self.template.id);
            console.log('User updated with id ', self.template.id);
        }
        reset();
    }
 
    function edit(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.msgtemplates.length; i++){
            if(self.template[i].id === id) {
                self.template = angular.copy(self.msgtemplates[i]);
                break;
            }
        }
    }
 
    function remove(id){
        console.log('id to be deleted', id);
        if(self.template.id === id) {//clean form if the user to be deleted is shown there.
            reset();
        }
        deleteUser(id);
    }
 
 
    function reset(){
        self.template={id:null,messageTemplate:''};
        $scope.myForm.$setPristine(); //reset Form
    }
 
}]);