// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102/112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2022T1, Assignment 8
 * Name: Tom Green
 * Username: greenthom
 * ID: 300536064
 */

import ecs100.*;
import java.awt.Color;
import javax.swing.JColorChooser;

/**
 * A simple drawing program.
 * The user can select from a variety of tools and options using the buttons and
 *   elements down the left side, and can use the mouse to add elements to the drawing
 *   according to the current tool and options
 * Note, most of the "action" in the program happens in response to mouse events;
 *   the buttons, textFields, and sliders mostly record information that is used
 *   later by the mouse responding.
 */


public class MiniPaint{

    // fields to remember:
    //  - the "tool" - what will be drawn when the mouse is next released.
    //                 may be a shape, or an image, or a caption,
    //    [Completion] or eraser, or flower
    //  - whether the shape should be filled or not
    //  - the position the mouse was pressed,
    //  - the string for the text caption
    //  - the width of the lines and the font size of the text captions.
    //  - [Completion] the name of the image file
    //  - [Completion] the colors for the border and fill for shapes and captions

    private String tool = "Line";   // the current tool, governing what the mouse will do.
                                    // Initial value is "Line";  changed by the buttons.
    private double lineWidth = 1;
    private double DIAM = 10;
    private double xpos;
    private double ypos;
    private double textSize = 10;
    private String text;
    private double eraserSize = 10;
    private boolean fill = false;
    private String image;
    double radius = 50;
    private Color lineColor = Color.black;
    private Color fillColor = Color.black;
    /**
     * Set up the interface: buttons, textfields, sliders,
     * listening to the mouse
    */
    public void setupGUI(){
        UI.setMouseMotionListener(this::doMouse);
        UI.addButton("Clear", UI::clearGraphics);
        UI.addButton("Line", this::doSetLine);
        UI.addButton("Rect", this::drawARect);
        UI.addButton("Oval", this::doSetOval);
        UI.addTextField("Text", this::setWord);
        UI.addButton("Eraser", this::eraser);
        UI.addSlider("Line Width", 1, 19, 2, this::setWidth);
        UI.addSlider("Text Size", 1, 19, 2, this::setText);
        UI.addSlider("Eraser Size", 1, 19, 2, this::eraser);
        UI.addButton("Fill/No Fill", this::fill);
        UI.addButton("Fill Color", this::fillColor);
        UI.addButton("Line Color", this::lineColor);
        UI.addButton("Image",this::image);
        UI.addButton("Flower", this::flower);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(0.0);
    }

    public void doSetLine(){
        this.tool = "Line";
    }
    public void drawARect(){
        this.tool = "Rect";
    }
    public void doSetOval(){
        this.tool = "Oval";
    }
    public void setWord(String text){
        this.tool = "Text";
        this.text = text;
    }
    public void eraser(){
        this.tool = "Eraser";
    }
    public void setWidth(double value){
         this.lineWidth = value;
    }
    public void setText(double value){
        this.textSize = value;
    }
    public void eraser(double value){
        this.eraserSize = value;
    }
    public void fill(){
        this.fill = !(this.fill);
    }
    public void fillColor(){
        this.fillColor = JColorChooser.showDialog(null, "", this.fillColor);
    }
    public void lineColor(){
        this.lineColor = JColorChooser.showDialog(null, "", this.lineColor);
    }
    public void image(){
        this.tool = "Image";
        this.image = (UIFileChooser.open("Choose Image"));
    }
    public void flower(){
        this.tool = "Flower";
    }
    
    /**
     * Respond to mouse events
     * When pressed, remember the position.
     * When released, draw what is specified by current tool
     * Uses the value stored in the field to determine which kind of tool to draw.
     *  It should call the drawALine, drawARectangle, drawAnOval, etc, methods
     *  passing the pressed and released positions
     * [Completion] should respond to "dragged" events also to do erasing
     */
    public void doMouse(String action, double x, double y) {
        if (action.equals("pressed")){
            this.xpos = x;
            this.ypos = y;
        }
        else if (action.equals("released")){
            if (this.tool.equals("Line")){
                this.drawALine(this.xpos, this.ypos, x, y);
            }
            if(this.tool.equals("Rect")){
                this.drawARectangle(this.xpos, this.ypos,x,y);
            }
            if(this.tool.equals("Oval")){
                this.drawAnOval(this.xpos,this.ypos,x,y);
            }
            if(this.tool.equals("Text")){
                this.setWord(x,y);
            }
            if (this.tool.equals("Image")){
                this.drawAnImage(this.xpos,this.ypos,x,y);
            }
            if (this.tool.equals("Flower")){
                this.drawAFlower(this.xpos,this.ypos,radius);
            }
        }
        if(action.equals("dragged")){
            if (this.tool.equals("Eraser")){
                UI.eraseOval(x-eraserSize/2, y-eraserSize,eraserSize,eraserSize);
            }
        }
    }
    
    /**
     * Draw a line between the two positions (x1, y1) and (x2, y2)
     */
    public void drawALine(double x1, double y1, double x2, double y2){
        UI.setLineWidth(this.lineWidth);
        UI.setColor(this.lineColor);
        UI.drawLine(x1,y1,x2,y2);
    }

    /**
     * Draw a rectangle between the two diagonal corners
     * [Completion] Works out the left, top, width, and height 
     * Then draws the rectangle, based on the options
     */
    public void drawARectangle(double x1, double y1, double x2, double y2){
        if (this.fill == true){
            UI.setLineWidth(this.lineWidth);
            UI.setColor(this.fillColor);
            UI.fillRect(xpos, ypos, x2-xpos,y2-ypos);
        } else if (this.fill == false){
            UI.setColor(this.lineColor);
            UI.drawRect(xpos,ypos,x2-xpos,y2-ypos);
        }
    }
    
    /**
     * Draw an oval to fit the rectangle between the the two diagonal corners
     * [Completion] Works out the left, top, width, and height 
     * Then draws the oval, based on the options
     */
    public void drawAnOval(double x1, double y1, double x2, double y2){
        if (this.fill == true){
            UI.setLineWidth(this.lineWidth);
            UI.setColor(this.fillColor);
            UI.fillOval(xpos, ypos, x2-xpos,y2-ypos);
        } else if (this.fill == false){
            UI.setColor(this.lineColor);
            UI.drawOval(xpos,ypos,x2-xpos,y2-ypos);
        }
        }
    
    public void setWord(double x, double y){
        UI.setFontSize(textSize);
        UI.setColor(this.fillColor);
        UI.drawString(this.text,x,y);
        }
        
    public void drawAnImage(double x1, double y1, double x2, double y2){
         if (x2-x1 > 5 || y2-y1 > 5){
             UI.drawImage(this.image,x1,y1,x2,y2);
         }
         else{
             UI.drawImage(this.image,x1,y1,x2,y2);
         }
    }

    /** [Completion]
     * Draws a simple flower with 6 petals, centered at (x,y) with the given radius
     */
    public void drawAFlower(double x, double y, double radius){
        UI.setColor(Color.yellow);
        UI.fillOval(x,y,radius,radius);
        for(double i=0;i<6;i++){
            UI.setColor(this.fillColor);
            double h=2*radius;
            double x5=Math.cos(Math.PI *i/6.0) * h + x;
            double y5 = Math.sin(Math.PI *i/6.0) * h * y;
            UI.fillOval(x5,y5,radius,radius);

    }
    }   
    
    public static void main(String[] arguments){
        MiniPaint mp = new MiniPaint();
        mp.setupGUI();
    }
}
