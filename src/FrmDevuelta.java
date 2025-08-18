import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import javax.swing.JTextField;

public class FrmDevuelta extends JFrame {

    // variables globales o de clase
    private int[] denominaciones = new int[] {100000, 50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50};
    private int[] existencias = new int[denominaciones.length];
    private JComboBox cmbDenominacion;
    private JTextField txtExistencia;

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
        btnActualizarExistencia.setBounds(10, 40, 190, 25);
        getContentPane().add(btnActualizarExistencia);

        txtExistencia = new JTextField();
        txtExistencia.setBounds(200, 40, 100, 25);
        getContentPane().add(txtExistencia);

        // eventos para la lectura de las existencias
        cmbDenominacion.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarActualExistencias();
            }
        });
    }

    private void consultarActualExistencias() {
        int existencia = existencias[cmbDenominacion.getSelectedIndex()];
        txtExistencia.setText(String.valueOf(existencia));
    }

    private void actualizarExistencia() {
        existencias[cmbDenominacion.getSelectedIndex()] = Integer.parseInt(txtExistencia.getText());
    }

}
