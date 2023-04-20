import { AuthorComponent } from './components/utility/author/author.component';
import { ArtiklComponent } from './components/main/artikl/artikl.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DobavljacComponent } from './components/main/dobavljac/dobavljac.component';
import { PorudzbinaComponent } from './components/main/porudzbina/porudzbina.component';
import { HomeComponent } from './components/utility/home/home.component';
import { AboutComponent } from './components/utility/about/about.component';

const routes: Routes = [
  {path:'artikl', component:ArtiklComponent},
  {path:'dobavljac', component:DobavljacComponent},
  {path:'porudzbina', component:PorudzbinaComponent},
  {path:'home', component:HomeComponent},
  {path:'about', component:AboutComponent},
  {path:'author', component:AuthorComponent},
  {path:'',redirectTo:'home',pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
