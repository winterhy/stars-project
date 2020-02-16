<#assign content>


<h1 class="title"> STARS </h1>
<p class="subtitle center">Welcome to Stars.</p>
<p class="subtitle center">Please load a file first.</p>

<div id="wrapper">
<div id="bgleft">
<p class="text left">Search here for how many neighboring stars a point
  in the universe or a star in the universe has. Enter the number of neighbors
  you want to see followed by a space and either a name of a star in quotes or
  a 3D cartesian coordinate of a point separated by spaces.
<form method="POST" action="/neighbors">
<p class="gold left"><label for="neighbors">SEARCH BY NEIGHBORS: </label><br></p>
<textarea name="neighbors" id="neighbors" class="left"></textarea><br>
<input type="submit" class="left">
</form>
</p>


<p class="text left">Search here for how many stars a point in the universe or a star in the
  universe has near its radius. Enter the radius
  followed by a space and either a name of a star in quotes or
  a 3D cartesian coordinate of a point separated by spaces.
<form method="POST" action="/radius">
<p class="gold left"><label for="radius">SEARCH BY RADIUS: </label><br></p>
<textarea name="radius" id="radius" class="left"></textarea><br>
<input type="submit" class="left">
</form>
</p>
</div>

<div id="bgright">
<p class="gold right">RESULTS: </p>
  <p class="text right">${neighbors}</p>
  <p class="text right">${radius}</p>
</div>

</div>
</#assign>
<#include "main.ftl">