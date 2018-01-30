// Display point
#include <GL/glut.h>
void init();

//Boundry
void display(){

    // glColor3f(r,g,b) Specifying color to any object
    glColor3f(1.0,1.0,1.0);
    glLineWidth(2);
    glBegin(GL_LINES); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(-1,0);
        glVertex2f(1,0);
        glVertex2f(0,-1);
        glVertex2f(0,1);

    glEnd();
    glFlush();
    display1();
}

// 1st quadrant
void display1(){

    // glColor3f(r,g,b) Specifying color to any object
    glColor3f(0.0,1.0,0.0);
    glLineWidth(2);
    glBegin(GL_LINE_STRIP); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(0,0);
        glVertex2f(0.5,-0.9);
        glVertex2f(1,0);

    glEnd();
    glBegin(GL_LINE_STRIP); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(0.2,0);
        glVertex2f(0.5,-0.6);
        glVertex2f(0.8,0);

    glEnd();
    glBegin(GL_LINE_STRIP); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(0.4,0);
        glVertex2f(0.5,-0.3);
        glVertex2f(0.6,0);

    glEnd();
    glFlush();
    display2();
}

void display2(){

    // glColor3f(r,g,b) Specifying color to any object
    glColor3f(1.0,0.0,0.0);
    glLineWidth(2);
    glBegin(GL_LINE_STRIP); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(0,0);
        glVertex2f(-0.5,0.9);
        glVertex2f(-1,0);

    glEnd();
    glBegin(GL_LINE_STRIP); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(-0.2,0);
        glVertex2f(-0.5,0.6);
        glVertex2f(-0.8,0);

    glEnd();
    glBegin(GL_LINE_STRIP); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(-0.4,0);
        glVertex2f(-0.5,0.3);
        glVertex2f(-0.6,0);

    glEnd();
    glFlush();
    display3();
}

void display3(){

    // glColor3f(r,g,b) Specifying color to any object
    glColor3f(0.0,0.0,1.0);
    glLineWidth(2);
    glBegin(GL_LINE_STRIP); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(0,0);
        glVertex2f(0.9,0.5);
        glVertex2f(0,1);

    glEnd();
    glBegin(GL_LINE_STRIP); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(0,0.2);
        glVertex2f(0.6,0.5);
        glVertex2f(0,0.8);

    glEnd();
    glBegin(GL_LINE_STRIP); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(0,0.4);
        glVertex2f(0.3,0.5);
        glVertex2f(0,0.6);

    glEnd();
    glFlush();
    display4();
}

void display4(){

    // glColor3f(r,g,b) Specifying color to any object
    glColor3f(0.0,1.0,1.0);
    glLineWidth(2);
    glBegin(GL_LINE_STRIP); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(0,0);
        glVertex2f(-0.9,-0.5);
        glVertex2f(0,-1);

    glEnd();
    glBegin(GL_LINE_STRIP); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(0,-0.2);
        glVertex2f(-0.6,-0.5);
        glVertex2f(0,-0.8);

    glEnd();
    glBegin(GL_LINE_STRIP); // Draw Lines. Only 2 verteices.

        //Specify the points coordinates b/w -1 & 1
        glVertex2f(0,-0.4);
        glVertex2f(-0.3,-0.5);
        glVertex2f(0,-0.6);

    glEnd();
    glFlush();
    // display2();
}
int main(int argc,char *argv[]){
    glutInit(&argc,argv);
    glutInitWindowSize(500,500);
    glutInitWindowPosition(70,70);
    glutCreateWindow("Basic Window");
    init();
    glutDisplayFunc(display); // Resize with and without the function see the diff
    // display();

     // glutDisplayFunc(display2); Here disp2 overlapps disp1 so disp1 cannot be seen


    glutMainLoop();
    return 0;
}


void init(){
    glClearColor(0,0,0,0); // RGB-A Specify the color. Won't Change the color
    glClear(GL_COLOR_BUFFER_BIT); // Removes the color from COLOR_BUFFER_BIT after removing everything it'll fill the buffer with specified color
    glFlush(); //Each one is rendered in a proper manner. (Here.. Finishes the job of the above functions)    
}

//glutDisplayFunc(display_func_name) mandatory to invoke any function