import { Component, OnInit,CUSTOM_ELEMENTS_SCHEMA, Input } from '@angular/core';
import { EmployeedbService } from '../employees/services/employeedb.service';
import { FlatTreeControl } from '@angular/cdk/tree';
import { MatTreeFlattener, MatTreeFlatDataSource } from '@angular/material/tree';

import { User } from '@modules/auth/models';
import { number } from 'ngx-custom-validators/src/app/number/validator';

interface Node {
  name: string;
  children?: Node[];
}

var TREE_DATA: Node[] = [
  {
    name: 'Fruit',
    children: [
      {name: 'Apple'},
      {name: 'Banana'},
      {name: 'Fruit loops'},
    ]
  }, {
    name: 'Vegetables',
    children: [
      {
        name: 'Green',
        children: [
          {name: 'Broccoli'},
          {name: 'Brussels sprouts'},
        ]
      }, {
        name: 'Orange',
        children: [
          {name: 'Pumpkins'},
          {name: 'Carrots'},
        ]
      },
    ]
  },
];

/** Flat node with expandable and level information */
interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}

@Component({
  selector: 'sb-heirarcy',
  templateUrl: './hierarcy.component.html',
  styleUrls: ['./hierarcy.component.scss'],
})
export class HierarcyComponent implements OnInit {

  @Input('currentEmployee') currentEmployee!:User;
  highestEmployee!:User;

  private _transformer = (node: Node, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
    };
  }

  treeControl = new FlatTreeControl<ExampleFlatNode>(
      node => node.level, node => node.expandable);

  treeFlattener = new MatTreeFlattener(
      this._transformer, node => node.level, node => node.expandable, node => node.children);

  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  constructor(private employeedbService:EmployeedbService) {
    this.employeedbService.getEmployee("1").subscribe(e=>{
      this.highestEmployee = e;
      this.loadHierarchy();
    })
  }

  hasChild = (_: number, node: ExampleFlatNode) => node.expandable;
  
  ngOnInit(): void {
  }

  loadHierarchy(){
    var val: Map<string,User[]>;
    this.employeedbService.getEmployeesFullHierarchy().subscribe(x=>{
      val = x;
      this.fillDownHierarchy(val);
    });
  }

  fillDownHierarchy(val:Map<string,User[]>){
    
    var emp:string = this.currentEmployee.id;

    var data:Node[] = [];
    var node:Node = {"name":this.currentEmployee.firstName + " - "+ this.currentEmployee.designation + "(You)"};

    node = this.fillDownHierarchyUtility(val,node,emp);
    data.push(node)
    this.fillUpHeirarchy(emp,data);
  }

  fillDownHierarchyUtility(val:Map<string,User[]>,tempNode:Node,empId:string):Node{
  
    if(!(<any>val)[empId]){
      return tempNode;
    }
    
    tempNode.children = [];
    for(var x in (<any>val)[empId]){
  
      var id = (<any>val)[empId][x].id;
      var name = (<any>val)[empId][x].firstName;
      var designation = (<any>val)[empId][x].designation;
      var y:Node = {"name":name+" - "+designation};
      var z:Node = this.fillDownHierarchyUtility(val,y,id);
  
      tempNode.children.push(z);
    }
    return tempNode;
  }

  fillUpHeirarchy(empId:string,data:Node[]){
    this.employeedbService.getEmployeeUpHierarchy(empId).subscribe(x=>{
      this.fillUpHeirarchyUtility(x,data,empId);
    })
  }

  fillUpHeirarchyUtility(x:Map<string,User>,data:Node[],empId:string){

    var node:Node = {"name":""};
    var tempNode:Node = node;
    var count:number = 0;
    for(var i in x){

      var id = (<any>x)[i].id;
      var name = (<any>x)[i].firstName;
      var designation = (<any>x)[i].designation;

      if(id == empId){
        break;
      }

      if(count==0){
        console.log("here");
        node.name = name+" - "+designation;
        tempNode = node;
      }
      else{
        var xtra:Node = {"name":name+" - "+designation};
        tempNode.children = [];
        tempNode.children.push(xtra);
        tempNode = xtra;
      }
      count+=1;
    }

    if(count!=0){
      var final_data:Node[] = [];
      tempNode.children = data;
      final_data.push(node);
  
      this.dataSource.data = final_data;
    }
    else{
      this.dataSource.data = data;
    }

    console.log(this.dataSource.data);
  }

  amICurrentUser(node:Node):boolean{
    if(node.name==this.currentEmployee.firstName+" - "+this.currentEmployee.designation+"(You)") { return true;}
    //"Alireza - Senior Associate(You)"
    return false;
  }

}