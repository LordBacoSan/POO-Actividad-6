package CRUD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AplicacionCRUD extends JFrame {
    private JTextField campoNombre, campoIdentificacion;
    private JList<String> listaPersonas;
    private DefaultListModel<String> modeloListaPersonas;

    private List<Persona> personas;

    public AplicacionCRUD() {
        setTitle("Aplicación CRUD");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panelEntrada = new JPanel(new GridLayout(2, 2));
        panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel etiquetaNombre = new JLabel("Nombre:");
        campoNombre = new JTextField();
        panelEntrada.add(etiquetaNombre);
        panelEntrada.add(campoNombre);

        JLabel etiquetaIdentificacion = new JLabel("Identificación:");
        campoIdentificacion = new JTextField();
        panelEntrada.add(etiquetaIdentificacion);
        panelEntrada.add(campoIdentificacion);

        add(panelEntrada, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton botonCrear = new JButton("Crear");
        botonCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearPersona();
            }
        });
        panelBotones.add(botonCrear);

        JButton botonLeer = new JButton("Leer");
        botonLeer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                leerPersonas();
            }
        });
        panelBotones.add(botonLeer);

        JButton botonActualizar = new JButton("Actualizar");
        botonActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarPersona();
            }
        });
        panelBotones.add(botonActualizar);

        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarPersonas();
            }
        });
        panelBotones.add(botonEliminar);

        add(panelBotones, BorderLayout.CENTER);

        modeloListaPersonas = new DefaultListModel<>();
        listaPersonas = new JList<>(modeloListaPersonas);
        JScrollPane panelScroll = new JScrollPane(listaPersonas);
        panelScroll.setPreferredSize(new Dimension(400, 200));
        add(panelScroll, BorderLayout.SOUTH);

        personas = new ArrayList<>();

        setVisible(true);
    }

    private void crearPersona() {
        String nombre = campoNombre.getText();
        String identificacion = campoIdentificacion.getText();

        if (nombre.isEmpty() || identificacion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return;
        }

        Persona persona = new Persona(nombre, identificacion);
        personas.add(persona);
        modeloListaPersonas.addElement(persona.getNombre());

        JOptionPane.showMessageDialog(this, "Persona creada: " + nombre);
        limpiarCampos();
    }

    private void leerPersonas() {
        JOptionPane.showMessageDialog(this, "Personas creadas:\n" + personas.toString());
    }

    private void actualizarPersona() {
        int indiceSeleccionado = listaPersonas.getSelectedIndex();
        if (indiceSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una persona para actualizar.");
            return;
        }

        String nombre = campoNombre.getText();
        String identificacion = campoIdentificacion.getText();

        if (nombre.isEmpty() || identificacion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return;
        }

        Persona persona = personas.get(indiceSeleccionado);
        persona.setNombre(nombre);
        persona.setIdentificacion(identificacion);
        modeloListaPersonas.setElementAt(persona.getNombre(), indiceSeleccionado);

        JOptionPane.showMessageDialog(this, "Persona actualizada: " + nombre);
        limpiarCampos();
    }

    private void eliminarPersonas() {
        int[] indicesSeleccionados = listaPersonas.getSelectedIndices();
        if (indicesSeleccionados.length == 0) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una o más personas para eliminar.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar las personas seleccionadas?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            for (int i = indicesSeleccionados.length - 1; i >= 0; i--) {
                int indiceSeleccionado = indicesSeleccionados[i];
                personas.remove(indiceSeleccionado);
                modeloListaPersonas.removeElementAt(indiceSeleccionado);
            }

            JOptionPane.showMessageDialog(this, "Persona(s) eliminada(s)");
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        campoNombre.setText("");
        campoIdentificacion.setText("");
        listaPersonas.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                new AplicacionCRUD();
            }
        });
    }
}

class Persona {
    private String nombre;
    private String identificacion;

    public Persona(String nombre, String identificacion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Identificación: " + identificacion;
    }
}