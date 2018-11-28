//Ethan Febinger

import java.util.*;//Arraylist, etc
import javax.swing.*;//Jframe
import java.awt.*;//Graphics
import java.awt.event.*;
import java.text.DecimalFormat;
import java.awt.image.*;



public class JuliaProgram extends JPanel implements AdjustmentListener
{
	JFrame frame;
	JScrollBar real;
	JScrollBar complex;
	JScrollBar xPower, yPower;
	JPanel scrollPanel,labelPanel,panelOfPanels;
	JLabel labelReal,labelComplex, labelX, labelY;
	double a=0,b=0;
	DecimalFormat df;
	BufferedImage image;
	int width=1000,height=700;
	double zoom=1;
	float maxIter=50;
	double zx,zy;
	int xPow=2, yPow=2;

	public JuliaProgram()
	{

		frame = new JFrame("Julia Set Program");

		frame.add(this);

		real=new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,6000);
		real.addAdjustmentListener(this);
		complex=new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,6000);
		complex.addAdjustmentListener(this);
		xPower=new JScrollBar(JScrollBar.HORIZONTAL,2,0,0,6);
		xPower.addAdjustmentListener(this);
		yPower=new JScrollBar(JScrollBar.HORIZONTAL,2,0,0,6);
		yPower.addAdjustmentListener(this);

		String pattern = "0.000";
		df = new DecimalFormat(pattern);

		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

		labelReal = new JLabel("Real:"+df.format(a));
		labelComplex = new JLabel("Complex"+df.format(b));
		labelX = new JLabel("xPower:"+xPow);
		labelY = new JLabel("yPower:"+yPow);

		scrollPanel = new JPanel();
		labelPanel = new JPanel();
		panelOfPanels = new JPanel();

		labelPanel.add(labelReal);
		labelPanel.add(labelComplex);
		labelPanel.add(labelX);
		labelPanel.add(labelY);

		scrollPanel.add(real);
		scrollPanel.add(complex);
		scrollPanel.add(xPower);
		scrollPanel.add(yPower);

		scrollPanel.setLayout(new GridLayout(4,1));
		labelPanel.setLayout(new GridLayout(4,1));

		real.setPreferredSize(new Dimension(400,20));
		complex.setPreferredSize(new Dimension(400,20));
		complex.setPreferredSize(new Dimension(400,20));
		xPower.setPreferredSize(new Dimension(400,20));
		yPower.setPreferredSize(new Dimension(400,20));



		panelOfPanels.add(BorderLayout.WEST,labelPanel);
		panelOfPanels.add(BorderLayout.CENTER,scrollPanel);

		frame.add(BorderLayout.SOUTH,panelOfPanels);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width,height+100);
		frame.setVisible(true);

	}

	public void paintComponent(Graphics gg)
	{
		super.paintComponent(gg);

		for(int x=0;x<width;x++)
			for(int y=0;y<height;y++){

				zx= 1.5*(x-width/2)/(0.5*zoom*width);
				zy= (y-height/2)/(0.5*zoom*height);

				float i=maxIter;

				while((power(zx,xPow)+power(zy,yPow)<6) && (i>0) ){
					double d = power(zx,xPow)-power(zy,yPow)+ a;

					zy=2*zx*zy+ b;
					zx =d;

					i--;
				}

				int c;
				if(i>0)
					c = Color.HSBtoRGB((maxIter / i) % 1,1, 1);
				else c = Color.HSBtoRGB(maxIter / i, 1, 0);

				image.setRGB(x,y,c);


			}

		gg.drawImage(image,0,0,null);



	}

	public void adjustmentValueChanged(AdjustmentEvent e){
		if(e.getSource()==real){
			a=real.getValue()/1000.0;
			labelReal.setText("Real:"+df.format(a));
		}
		if(e.getSource()==complex){
			b=complex.getValue()/1000.0;
			labelComplex.setText("Complex:"+df.format(b));
		}
		if(e.getSource()==xPower){
			xPow=xPower.getValue();
			labelX.setText("xPower:"+xPow);
		}
		if(e.getSource()==yPower){
			yPow=yPower.getValue();
			labelY.setText("yPower:"+yPow);
		}


		repaint();
	}

	public static void main(String args[])
	{
		JuliaProgram app=new JuliaProgram();
	}

	public double power(double base, int p){
		double x=0;
		switch(p){
			case 0: x=1;
				break;
			case 1: x=base;
				break;
			case 2: x= base*base;
				break;
			case 3: x= base*base*base;
				break;
			case 4: x= base*base*base*base;
				break;
			case 5: x= base*base*base*base*base;
				break;
			case 6: x= base*base*base*base*base*base;
				break;
		}

		return x;
	}
}

