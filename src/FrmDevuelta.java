import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class FrmDevuelta extends JFrame {

    // variables globales o de clase
    private int[] denominaciones = new int[] {100000, 50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50};
    private int[] existencias = new int[denominaciones.length];
    private String[] encabezados = new String[] {"Cantidad", "Presentación", "Denominación"};
    private JComboBox cmbDenominacion;
    private JTextField txtExistencia;
    private JTextField txtDevuelta;
    private JTable tblDevuelta;

    // metodo constructor
    public FrmDevuelta() {
        setTitle("Calculo de Devuelta");
        setSize(500,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null); // anular diseño predeterminado

        JLabel lblDenominacion = new JLabel("Denominación:"); // declarar
        lblDenominacion.setBounds(100, 10, 100, 25); // ubicar
        getContentPane().add(lblDenominacion); // agregar

        cmbDenominacion = new JComboBox();
        cmbDenominacion.setBounds(200, 10, 100, 25);
        getContentPane().add(cmbDenominacion);
        
        // pasar las denominaciones de entero a string
        String[] strDenominaciones = new String[denominaciones.length];
        for (int i = 0; i < denominaciones.length; i++){
            strDenominaciones[i] = String.valueOf(denominaciones[i]);
        }

        // asignar el modelo de datos a la lista desplegable
        cmbDenominacion.setModel(new DefaultComboBoxModel(strDenominaciones));
    
        JButton btnActualizarExistencia = new JButton("Actualizar Existencia:");
        btnActualizarExistencia.setBounds(10, 40, 180, 25);
        getContentPane().add(btnActualizarExistencia);

        txtExistencia = new JTextField();
        txtExistencia.setBounds(200, 40, 100, 25);
        getContentPane().add(txtExistencia);

        // eventos para la lectura de las existencias
        cmbDenominacion.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarActualExistencia();
            }
        });

        btnActualizarExistencia.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarExistencia();
            }

        });

        JLabel lblDevuelta = new JLabel("Valor de Devolver:");
        lblDevuelta.setBounds(10, 70, 150, 25);
        getContentPane().add(lblDevuelta);

        txtDevuelta = new JTextField();
        txtDevuelta.setBounds(170, 70, 100, 25);
        getContentPane().add(txtDevuelta);

        JButton btnDevuelta = new JButton("Calcular Devuelta:");
        btnDevuelta.setBounds(280, 70, 150, 25);
        getContentPane().add(btnDevuelta);

        tblDevuelta = new JTable();
        JScrollPane spDevuelta = new JScrollPane(tblDevuelta);
        spDevuelta.setBounds(10, 100, 480, 250);
        getContentPane().add(spDevuelta);

        DefaultTableModel dtmDevuelta = new DefaultTableModel(null, encabezados);
        tblDevuelta.setModel(dtmDevuelta);

        // evento para el calculo de la devuelta
        btnDevuelta.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularDevuelta();
            }
        });

    }

    private void consultarActualExistencia() {
        int existencia = existencias[cmbDenominacion.getSelectedIndex()];
        txtExistencia.setText(String.valueOf(existencia));
    }

    private void actualizarExistencia() {
        existencias[cmbDenominacion.getSelectedIndex()] = Integer.parseInt(txtExistencia.getText());
    }

    private void calcularDevuelta() {
        int valorDevuelta = Integer.parseInt(txtDevuelta.getText());

        int[] devuelta = new int[denominaciones.length];

        int i = 0;
        while(valorDevuelta > 0 && i < denominaciones.length) {
            if(valorDevuelta >= denominaciones[i]) {
                int cantidadNecesaria = (valorDevuelta - valorDevuelta % denominaciones[i]) / denominaciones[i];
                devuelta[i] = cantidadNecesaria <= existencias[i] ? cantidadNecesaria : existencias[i];

                valorDevuelta -= devuelta[i] * denominaciones[i];
            }
            i++;
        }
        
        int totalFilas = 0;
        for (i = 0; i < devuelta.length; i++) {
            if(devuelta[i] > 0) {
                totalFilas++;
            }
        }

        String[][] resultado = new String[totalFilas][encabezados.length];
        int fila = 0;
        for (i = 0; i < devuelta.length; i++) {
            if(devuelta[i] > 0) {
                resultado[fila][0]= String.valueOf(devuelta[i]);
                resultado[fila][1]= denominaciones[i] > 1000 ? "billete" : "moneda";
                resultado[fila][2]= String.valueOf(denominaciones[i]);
                fila++;
            }
        }

        DefaultTableModel dtmDevuelta = new DefaultTableModel(resultado, encabezados);
        tblDevuelta.setModel(dtmDevuelta);

        if(valorDevuelta > 0) {
            JOptionPane.showMessageDialog(null, "Queda pendiente $ " + valorDevuelta);
        }

    }

}
