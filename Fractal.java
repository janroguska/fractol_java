import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;

public class Fractal extends JFrame
{
	static final int WIDTH = 800;
	static final int HEIGHT = 600;
	Canvas canvas;
	BufferedImage fractal;

	public Fractal()
	{
		setProperties();
		addCanvas();
		draw();
		this.setVisible(true);
	}

	public void setProperties()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void addCanvas()
	{
		canvas = new Canvas();
		fractal = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		canvas.setVisible(true);
		this.add(canvas, BorderLayout.CENTER);
	}

	public static void main(String[] args)
	{
		new Fractal();
	}

	private	int	colour(int j)
	{
		int		red;
		int		green;
		int		blue;
		double	hex;
		double	tmp;

		red = 255;
		green = 255;
		tmp = 0;
		while (j >= 1)
		{
			hex = j % 255;
			// if (e->colour != 1)
			// 	hex *= 10;
			tmp += hex;
			tmp *= 10;
			j /= 255;
		}
		blue = (int)(Math.sin(0.16 * tmp + 1) * 230 + 25);
		// red = (e->colour == 0 ? (sin(0.016 * tmp + 4) * 230 + 25) : 0xf00000);
		// green = (e->colour == 1 ? (sin(0.013 * tmp + 2) * 230 + 25) : 0x0f0000);
		// blue = (e->colour == 2 ? (sin(0.01 * tmp + 1) * 230 + 25) : 0x00000f);
		return(red + green + blue);
	}

	public void draw()
	{
		int		i;
		int		j;
		int		k;
		int		colour;


		i = 0;
		while (i < HEIGHT)
		{
			k = 0;
			while (k < WIDTH)
			{
				j = mandelbrot(i, k);
				colour = colour(j);
				if (j != 0)
					fractal.setRGB(k, i, colour);
				else
					fractal.setRGB(k, i, 0);
				k++;
			}
			i++;
		}
		canvas.repaint();
	}

	public double	get_x0(int k)
	{
		double	w;
		double z;

		w = 5 / (double)WIDTH;
		z = k - WIDTH / 2.0;
		z = (z * w);
		return (z);
	}

	public double	get_y0(int i)
	{
		double	h;
		double z;

		h = 4 / (double)HEIGHT;
		z = i - HEIGHT / 2.0;
		z = (z * h);
		return (z);
	}

	public int mandelbrot(int i, int k)
	{
		int		l;
		int		m;
		double	x;
		double	y;
		double	xtemp;
		double	x0;
		double	y0;

		x0 = get_x0(k);
		y0 = get_y0(i);
		x = 0;
		y = 0;
		l = 0;
		m = 0;
		while ((x * x + y * y) < 4 && l < 256)
		{
			xtemp = ((x * x) - (y * y) + x0);
			y = ((2 * x * y) + y0);
			x = xtemp;
			if ((x * x + y * y) > 4)
				m = l;
			l++;
		}
		return (m);
	}

	private class Canvas extends JPanel
	{
		@Override public Dimension getPreferredSize()
		{
			return new Dimension (WIDTH, HEIGHT);
		}

		@Override public void paintComponent(Graphics drawingObj)
		{
			drawingObj.drawImage(fractal, 0, 0, null);
		}
	}
}