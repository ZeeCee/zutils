package player;

import java.awt.*;

import javax.swing.*;
import javax.sound.midi.*;

import java.util.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BeatBox {

    JPanel mainPanel;
    ArrayList<JCheckBox> checkboxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame theFrame;

    String[] instrumentNames = { "Bass Drum", "Closed Hi-Hat", "Open Hi-Hat",
	    "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom",
	    "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell",
	    "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga" };

    int[] instruments = { 35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58,
	    47, 67, 63 };

    public static void main(String[] args) {
	new BeatBox().buildGUI();

    }

    public void buildGUI() {
	theFrame = new JFrame("BeatBox");
	theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	BorderLayout layout = new BorderLayout(); // border layout manager
	JPanel background = new JPanel(layout); // create new JPanel with
						// specified layout manager

	background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	// More visual illustration of Borders can be found @
	// https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html

	checkboxList = new ArrayList<JCheckBox>();

	Box buttonBox = new Box(BoxLayout.Y_AXIS);

	JButton start = new JButton("Start");
	start.addActionListener(new MyStartListener());
	buttonBox.add(start);

	JButton stop = new JButton("Stop");
	stop.addActionListener(new MyStopListener());
	buttonBox.add(stop);

	// JButton upTempo = new JButton("Tempo Up");
	JButton upTempo = new JButton("Tempo +");
	upTempo.addActionListener(new MyStopListener());
	buttonBox.add(upTempo);

	// JButton downTempo = new JButton("Tempo Down");
	JButton downTempo = new JButton("Tempo -");
	downTempo.addActionListener(new MyDownTempoListener());
	buttonBox.add(downTempo);

	JButton savePattern = new JButton("Save");
	savePattern.addActionListener(new MySendListener());
	buttonBox.add(savePattern);
	
	JButton restorePatter = new JButton("Restore");
	restorePatter.addActionListener(new MyReadInListener());
	buttonBox.add(restorePatter);
	
	Box nameBox = new Box(BoxLayout.Y_AXIS);

	for (int i = 0; i < 16; i++) {
	    nameBox.add(new Label(instrumentNames[i])); // add nameboxes
							// vertically to
							// background/west
	}

	background.add(BorderLayout.EAST, buttonBox);
	background.add(BorderLayout.WEST, nameBox);

	theFrame.getContentPane().add(background);

	GridLayout grid = new GridLayout(16, 16);
	grid.setVgap(1);
	grid.setHgap(2);
	mainPanel = new JPanel(grid);
	background.add(BorderLayout.CENTER, mainPanel);

	for (int i = 0; i < 256; i++) {
	    JCheckBox c = new JCheckBox();
	    c.setSelected(false);
	    checkboxList.add(c);
	    mainPanel.add(c); // add checkboxes to GridLayout 16*16
	}

	setUpMidi();

	theFrame.setBounds(50, 50, 300, 300);
	theFrame.pack();
	theFrame.setVisible(true);
    }

    public void setUpMidi() {
	try {
	    sequencer = MidiSystem.getSequencer();
	    sequencer.open();
	    sequence = new Sequence(Sequence.PPQ, 4);
	    track = sequence.createTrack();
	    sequencer.setTempoInBPM(120);
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public void buildTrackAndStart() {
	int[] trackList = null;

	sequence.deleteTrack(track);
	track = sequence.createTrack();

	for (int i = 0; i < 16; i++) {
	    trackList = new int[16];
	    int key = instruments[i];

	    for (int j = 0; j < 16; j++) {
		JCheckBox jc = checkboxList.get(j + 16 * i);  // Hexadecimal 

		if (jc.isSelected()) {
		    trackList[j] = key;
		} else {
		    trackList[j] = 0;
		}
	    }

	    makeTracks(trackList);
	    track.add(makeEvent(176, 1, 127, 0, 16));

	}
	track.add(makeEvent(193, 9, 1, 0, 15));

	try {
	    sequencer.setSequence(sequence);
	    sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
	    sequencer.start();
	    sequencer.setTempoInBPM(120);
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public void makeTracks(int[] list) {

	for (int i = 0; i < 16; i++) {
	    int key = list[i];

	    if (key != 0) {
		track.add(makeEvent(144, 9, key, 100, i));
		track.add(makeEvent(128, 9, key, 100, i + 1));

	    }

	}

    };

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {

	MidiEvent event = null;
	try {
	    ShortMessage a = new ShortMessage();
	    a.setMessage(comd, chan, one, two);
	    event = new MidiEvent(a, tick);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return event;

    };

    // Inner classes:

    public class MyStartListener implements ActionListener {
	public void actionPerformed(ActionEvent a) {
	    buildTrackAndStart();
	}

    }

    public class MyStopListener implements ActionListener {
	public void actionPerformed(ActionEvent a) {
	    sequencer.stop();
	}

    }

    public class MyUpTempoListener implements ActionListener {
	public void actionPerformed(ActionEvent a) {
	    float tempoFactor = sequencer.getTempoFactor();
	    sequencer.setTempoFactor((float) (tempoFactor * 1.03));
	}

    }

    public class MyDownTempoListener implements ActionListener {
	public void actionPerformed(ActionEvent a) {
	    float tempoFactor = sequencer.getTempoFactor();
	    sequencer.setTempoFactor((float) (tempoFactor * 0.97));

	}
    }

    public class MySendListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent a) {
	    boolean[] checkState = new boolean[256];

	    for (int i = 0; i < 256; i++) {
		JCheckBox check = (JCheckBox) checkboxList.get(i);
		if (check.isSelected()) {
		    checkState[i] = true;
		}

	    }

	    try {
		FileOutputStream fileStream = new FileOutputStream(new File(
			"Checkbox.ser"));
		ObjectOutputStream os = new ObjectOutputStream(fileStream);
		os.writeObject(checkState);
		os.close();

	    } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}

    }

    public class MyReadInListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    boolean[] checkboxState = null;

	    try {
		FileInputStream fileIn = new FileInputStream(new File(
			"Checkbox.ser"));

		ObjectInputStream is = new ObjectInputStream(fileIn);
		checkboxState = (boolean[]) is.readObject();
		is.close();
		
	    } catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    } catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    } catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }

	    for (int i = 0; i < 256; i++) {
		JCheckBox check = (JCheckBox) checkboxList.get(i);
		if (checkboxState[i]) {
		    check.setSelected(true);
		} else {
		    check.setSelected(false);
		}
	    }

	    sequencer.stop();
	    buildTrackAndStart();
	}
    }
}
