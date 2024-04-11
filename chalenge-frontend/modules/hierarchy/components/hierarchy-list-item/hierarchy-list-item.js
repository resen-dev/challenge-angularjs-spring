angular.module('app.hierarchy')
.component('appHierarchyListItem', {
  templateUrl: './modules/hierarchy/components/hierarchy-list-item/hierarchy-list-item.html',
  bindings: {
    user: '<'
  },
  controller: HierarchyListItemController
});

function HierarchyListItemController(HierarchyFacade) {
  var $ctrl = this;
  
  $ctrl.expand = false
  
  $ctrl.expandHierarchy = () => {
    $ctrl.expand = !$ctrl.expand 
    
    if($ctrl.expand){
      HierarchyFacade.getSubordinates($ctrl.user)
    }else{
      $ctrl.user.subordinates = []
    }
  }
}
