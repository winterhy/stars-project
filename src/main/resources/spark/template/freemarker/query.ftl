<#assign content>

<h1 class="title"> STARS </h1>
  <p class="text">Welcome to Stars.</p>
  <p class="text">Please load a file first.</p>

  <p class="text">Search here for how many neighboring stars a point
  in the universe or a star in the universe has. Enter the number of neighbors
  you want to see followed by a space and either a name of a star in quotes or
  a 3D cartesian coordinate of a point separated by spaces.
<form method="POST" action="/neighbors">
  <p class="text"><label for="neighbors">SEARCH BY NEIGHBORS: </label><br></p>
  <textarea name="neighbors" id="neighbors" class="text"></textarea><br>
  <input type="submit">
</form>
</p>

<p class="text">${neighbors}</p>

<p class="text">Search here for how many stars a point in the universe or a star in the
  universe has near its radius. Enter the radius
  followed by a space and either a name of a star in quotes or
  a 3D cartesian coordinate of a point separated by spaces.
<form method="POST" action="/radius">
  <p class="text"><label for="radius">SEARCH BY RADIUS: </label><br></p>
  <textarea name="radius" id="radius" class="text"></textarea><br>
  <input type="submit">
</form>
</p>

  <p class="text">${radius}</p>

</#assign>
<#include "main.ftl">