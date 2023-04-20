# Fractal Generator

This program generate [1.x dimension](https://en.wikipedia.org/wiki/Hausdorff_dimension) fractals with 3 differents methods. 

* The first method "*Koch*" is something I've remade from this website [sciencevsmagic.net/fractal](sciencevsmagic.net/fractal).
* The second method "*Tree*" is one I've created to generate Trees. It has a 2 versions, Simple and Advanced.
* The third is a very simple version of the [LSystem](https://en.wikipedia.org/wiki/L-system) .

<p align="center">
	<img src="https://raw.githubusercontent.com/ejacquem/Fractal-Generator/main/resources/Presentation.png" width="700">
</p><br/>

# Summary

* **[Summary](#summary)**
* **[Features](#features)**
    * [preset](#preset)
	* [save](#save)
    * [animate](#animate)
	* [settings](#settings)
* **[Fractal parameters](#fractal-parameters)**
    * [Koch](#koch)
    * [Tree](#tree)
    * [LSystem](#lsystem)
* **[Tips](#tips)**
* **[Installation](#installation)**
* **[Images](#images)**
    * [Koch CurveEnhanced](#tree)
    * [Classic](#Classic)
    * [Beech](#Beech)
    * [Fern](#Fern)
    * [Flower](#Flower)
    * [Spiral](#Spiral)
    * [Sierpinski_Triangle](#Sierpinski_Triangle)
    * [Cool_Fractal](#Cool_Fractal)
* **[Contributing](#contributing)**
* **[Credits](#credits)**

# Features

* ### Preset
	
Presets are values that generate either famous fractal or ones that I found visually appealing. 

* ### Save
Save fractals you have generated as png images or in the form of text that you can load later.
	
* ### Animate
Generates at specific interval of time new fractals by changing the angle. 
* ### Settings

    * Lock skew angle / angle ratio:  If true, changing the angle value will change the skew angle value and keep the same "ratio". Changing skew angle will not change the angle value.


    * Center :

        Center Middle, center of fractal is the middle point of all lines

        Center Trunk, center of fractal is the base of the Trunk.
This will freeze the Trunk of the Tree and will not resize the fractal according to its size.
It's useful to prevent the tree to move around too much when changing the angle value.

    * Zoom: different  zoom modes
    * Number Of line Limit : change de maximum number of lines the fractals can have.

# Fractal parameters
* iterations : number of time the fractal repeat itself
* Random : attributes random values to the selected methods.

## Koch
* angle and skew angle : determines the rotation of the lines
* sides : determines general shape of fractal (1 line/2 lines/triangle/square/pentagon/hexagon/...)
* segments : determines the number of lines of the "spike"
* mirror : double the "spike" on each iteration


## Tree
* Leaves : if true, only render the last iteration of the branches aka the leaves.
* Trunk : if true, render the Trunk;
* length : Length of the branch (relative to its "root")
* position : position of the branch (100 is at the top, 0 at the bottom)

Simple : Each branch share the same value.


Advanced : Control each branch individually  with their own value.

## LSystem

This [website](http://paulbourke.net/fractals/lsys/) will explain better than me how it works. Nearly all LSystem presets are coming from there.

My program only implements 4 "commands" :

* F move forward by tracing a line
* \+ rotate right by a specific angle
* \- rotate left by a specific angle
* \| turn 180 degrees

You can add a maximum of 26 rules from A -> Z.

# Tips
* I recommend creating simple shapes at low iteration level first and then see how it goes at higher level.
It's also easier to understand how it works when there is only a few lines on the screen.
* The random button works very well for the Koch and Simple Tree methods, there is some sort of built-in symmetry in the way they generate and will often create nice looking shapes. For the Advanced tree methods, it allows a ton of liberty, therefore it will often generate complete nonsense. The same goes for the LSystem which is why there is no random button for this method.
* Fractals that reach Lines Limits while often have a "broken" appearance. Either lower the iteration levels or push the limit further. Keep in mind that the number of lines can go up very quickly and the program can crash if extremes values.
* You can change your saved fractal name by directly modifying the text File "SavedFratal.txt".

# Installation

This is an eclipse project, all classes are in the Fractal_Generator_V1\src folder.

# Releases

To just test the program, go to [**Releases**](https://github.com/ejacquem/Fractal-Generator/releases). You can download the first zip file and execute the .jar file.

This program is in [Java](https://www.java.com/download/ie_manual.jsp), so you need a recent enough version of java in order to work.

To save images or state of your fractal, you need to have "My fractal Images" folder and "SavedFratal.txt" text file in the same folder as the program. You might need to allow the program to have access to the text file.

# Images

### Koch CurveEnhanced
<p align="center">
	<img src="https://raw.githubusercontent.com/ejacquem/Fractal-Generator/main/resources/Koch_CurveEnhanced.png" width="500">
</p><br/>

### Classic
<p align="center">
	<img src="https://raw.githubusercontent.com/ejacquem/Fractal-Generator/main/resources/Classic.png" width="500">
</p><br/>

### Beech
<p align="center">
	<img src="https://raw.githubusercontent.com/ejacquem/Fractal-Generator/main/resources/Beech.png" width="500">
</p><br/>

### Fern
<p align="center">
	<img src="https://raw.githubusercontent.com/ejacquem/Fractal-Generator/main/resources/Fern.png" width="500">
</p><br/>

### Flower
<p align="center">
	<img src="https://raw.githubusercontent.com/ejacquem/Fractal-Generator/main/resources/Flower.png" width="500">
</p><br/>

### Spiral
<p align="center">
	<img src="https://raw.githubusercontent.com/ejacquem/Fractal-Generator/main/resources/Spiral.png" width="500">
</p><br/>

### Sierpinski_Triangle
<p align="center">
	<img src="https://raw.githubusercontent.com/ejacquem/Fractal-Generator/main/resources/Sierpinski_Triangle.png" width="500">
</p><br/>

### Cool_Fractal
<p align="center">
	<img src="https://raw.githubusercontent.com/ejacquem/Fractal-Generator/main/resources/Cool_Fractal.png" width="500">
</p>


# Contributing

Any sugestion are welcome! If you find any interesting shape feel free to share it somewhere.
You can save the current fractal state and copy/paste it here pretty easily like this :

TreeSimple : *MyFractalName* (15,300,2,0,true,false);

# Credits

* [**Lucas Jacquemin**](https://github.com/ejacquem) : Creator of the project.