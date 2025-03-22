package br.ufpa.view;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.awt.*;
import java.util.Map;

// Importações do projeto
import br.ufpa.controller.CharacterController;
import br.ufpa.model.Character;
import br.ufpa.model.SuperHero;
import br.ufpa.model.SuperVillain;
import br.ufpa.model.dao.CharacterDAOImpl;


public class Window extends JFrame {

    private JPanel albumPanel; // Painel do álbum
    // Campos do formulário (DECLARE-OS AQUI!)
    private JTextField txtName = new JTextField(); 
    private JTextArea txtDescription = new JTextArea(3, 20); 
    private JTextArea txtPowers = new JTextArea(3, 20); 
    private JTextField txtTeam = new JTextField(); 
    private JTextArea txtSkills = new JTextArea(3, 20); 
    private JTextField txtUniverse = new JTextField(15);
    private JTextField txtThreatLevel = new JTextField(5); 
    private JTextField txtImagePath = new JTextField();
    private JTextField txtVideoPath = new JTextField(); 


    public Window() {
        initUI();
    }

    private void initUI() {
        setTitle("Gerenciador de Heróis/Vilões");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel de cadastro
        JPanel registrationPanel = createRegistrationPanel();

        // Painel do álbum
        albumPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 colunas
        JScrollPane albumScroll = new JScrollPane(albumPanel);

        // Divisão principal
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                registrationPanel,
                albumScroll
        );
        splitPane.setDividerLocation(400);

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createRegistrationPanel() {
        JPanel registrationPanel = new JPanel(new BorderLayout(10, 10));
        registrationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // ------ Formulário Principal ------
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        addFormField(formPanel, "Nome:", txtName);
        addFormField(formPanel, "Descrição:", new JScrollPane(txtDescription));
        addFormField(formPanel, "Poderes:", new JScrollPane(txtPowers));
        addFormField(formPanel, "Grupo:", txtTeam);
        addFormField(formPanel, "Habilidades:", new JScrollPane(txtSkills));


        // --- Campos para Imagem e Vídeo ---
        JPanel mediaPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        txtImagePath = new JTextField();
        JButton btnBrowseImage = new JButton("Procurar");
        txtVideoPath = new JTextField();
        JButton btnBrowseVideo = new JButton("Procurar");
        mediaPanel.add(new JLabel("Imagem:"));
        mediaPanel.add(txtImagePath);
        mediaPanel.add(new JLabel(""));
        mediaPanel.add(btnBrowseImage);
        mediaPanel.add(new JLabel("Vídeo:"));
        mediaPanel.add(txtVideoPath);
        mediaPanel.add(new JLabel(""));
        mediaPanel.add(btnBrowseVideo);

        formPanel.add(new JLabel("Mídia:"));
        formPanel.add(mediaPanel); 

        JPanel commonFieldsPanel = new JPanel(new BorderLayout(10, 10));
        commonFieldsPanel.add(formPanel, BorderLayout.NORTH);
        commonFieldsPanel.add(mediaPanel, BorderLayout.CENTER);
        mainPanel.add(commonFieldsPanel, BorderLayout.NORTH);


        // --- Abas para Campos Específicos ---
        JTabbedPane tabbedPane = new JTabbedPane();
        // Heroi
        tabbedPane.setPreferredSize(new Dimension(100, 100)); 
        JPanel heroTab = new JPanel(new FlowLayout(FlowLayout.LEFT));
        heroTab.add(new JLabel("Universo (Marvel/DC):"));
        heroTab.add(txtUniverse);
        // Vilão
        JPanel villainTab = new JPanel(new FlowLayout(FlowLayout.LEFT));
        villainTab.add(new JLabel("Nível de Ameaça (1-10):"));
        villainTab.add(txtThreatLevel);

        tabbedPane.addTab("Herói", heroTab);
        tabbedPane.addTab("Vilão", villainTab);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);


        // --- Botões de Ação ---
        // Imagem
        btnBrowseImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(Window.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                txtImagePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        // Video
        btnBrowseVideo.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(Window.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                txtVideoPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });


        // Adicionar Personagem
        JButton btnAdd = new JButton("Adicionar");
        JButton btnClear = new JButton("Limpar");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnClear);
        buttonPanel.add(btnAdd);

        btnAdd.addActionListener(e -> {
            CharacterController controller = new CharacterController();
            try {
                Map<String, String> formData = getFormData();
                String type = getSelectedTabType(); // Determina a aba ativa
                controller.saveCharacter(formData, type);
                refreshAlbum();
            } catch (IllegalArgumentException | IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ------ Montagem Final ------
        registrationPanel.add(mainPanel, BorderLayout.CENTER);
        registrationPanel.add(buttonPanel, BorderLayout.SOUTH);

        return registrationPanel;
    }

    // Método auxiliar para adicionar campos com rótulo
    private void addFormField(JPanel panel, String label, JComponent component) {
        panel.add(new JLabel(label));
        panel.add(component);
    }

    // Método para criar um card de personagem
    public void refreshAlbum() {
        albumPanel.removeAll(); // Para limpar o painel

        CharacterDAOImpl dao = new CharacterDAOImpl();
        List<Character> characters = dao.getAllCharacters();

        for (Character character : characters) {
            JPanel card = createCharacterCard(character);
            albumPanel.add(card);
        }

        albumPanel.revalidate(); // Atualiza o layout
        albumPanel.repaint(); // Realoca o layout
    }

    private JPanel createCharacterCard(Character character) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createEtchedBorder());
        card.setPreferredSize(new Dimension(200, 250));


        // Nome e Tipo
        String type = (character instanceof SuperHero) ? "[HERÓI] " : "[VILÃO] ";
        JLabel nameLabel = new JLabel(type + character.getName());
        card.add(nameLabel, BorderLayout.NORTH);

        // Imagem (se existir)
        if (character.getImage() != null && character.getImage().length > 0) {
            ImageIcon icon = new ImageIcon(character.getImage());
            Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            card.add(new JLabel(new ImageIcon(scaledImage)), BorderLayout.CENTER);
        } else {
            card.add(new JLabel("Sem imagem"), BorderLayout.CENTER);
        }

        // Adiciona função clique para abrir detalhes
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showCharacterDetails(character);
            }
        });

        if (character.getVideo() != null && character.getVideo().length > 0) {
            JButton btnVideo = new JButton("Assistir Vídeo");
            btnVideo.addActionListener(e -> new VideoPlayer(character));
            card.add(btnVideo, BorderLayout.SOUTH);
        }

        return card;
    }


    // Método para adicionar campos de informação
    private void showCharacterDetails(Character character) {
        JDialog detailsDialog = new JDialog(this, "Detalhes do Personagem", true);
        detailsDialog.setSize(800, 700);
        detailsDialog.setLayout(new BorderLayout());


        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campos
        addInfoField(infoPanel, "Nome:", character.getName());
        addInfoField(infoPanel, "Descrição:", character.getDescription());
        addInfoField(infoPanel, "Poderes:", character.getPowers());
        addInfoField(infoPanel, "Grupo:", character.getTeam());
        addInfoField(infoPanel, "Habilidades:", character.getSkills());

        // Campos específicos
        if (character instanceof SuperHero) {
            addInfoField(infoPanel, "Universo:", ((SuperHero) character).getUniverse());
        } else if (character instanceof SuperVillain) {
            addInfoField(infoPanel, "Nível de Ameaça:", String.valueOf(((SuperVillain) character).getThreatLevel()));
        }

        // Imagem
        JLabel imgLabel = new JLabel();
        if (character.getImage() != null && character.getImage().length > 0) {
            ImageIcon icon = new ImageIcon(character.getImage());
            Image scaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaled));
        } else {
            imgLabel.setText("Sem imagem");
        }

        // Editar, Fechar e Excluir
        JPanel buttonPanel = new JPanel();
        JButton btnClose = new JButton("Fechar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnEdit = new JButton("Editar");

        // Adiciona ação para o botão de edição
        btnDelete.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                detailsDialog,
                "Tem certeza que deseja excluir " + character.getName() + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                CharacterController controller = new CharacterController();
                try {
                    controller.deleteCharacter(character.getId()); // Passando o ID
                    detailsDialog.dispose(); // Fechando a janela
                    refreshAlbum(); // Atualizando a lista
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        detailsDialog,
                        "Erro: " + ex.getMessage(),
                        "Falha na Exclusão",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Adiciona ação para o botão de edição
        btnEdit.addActionListener(e -> {
            detailsDialog.dispose(); // Fecha a janela de detalhes
            openEditWindow(character); // Abre a janela de edição
        });

        // Adiciona ação para o botão de fechar
        btnClose.addActionListener(e -> detailsDialog.dispose());
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClose);
        detailsDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Layout
        detailsDialog.add(infoPanel, BorderLayout.CENTER);
        detailsDialog.add(imgLabel, BorderLayout.EAST);
        detailsDialog.setLocationRelativeTo(this);
        detailsDialog.setVisible(true);
    }

    private void addInfoField(JPanel panel, String label, String value) {
        panel.add(new JLabel(label));
        panel.add(new JLabel(value));
    }


    // Método para abrir a janela de edição
    private void openEditWindow(Character character) {
        JDialog editDialog = new JDialog(this, "Editar Personagem", true);
        editDialog.setSize(600, 500);
        editDialog.setLayout(new BorderLayout());
    
        // --- Campos do Formulário  ---
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
    
        // Campos comuns 
        JTextField txtName = new JTextField(character.getName());
        JTextArea txtDescription = new JTextArea(character.getDescription());
        JTextArea txtPowers = new JTextArea(character.getPowers());
        JTextField txtTeam = new JTextField(character.getTeam());
        JTextArea txtSkills = new JTextArea(character.getSkills());
        JTextField txtImagePath = new JTextField(); // Campo para nova imagem 
        JTextField txtVideoPath = new JTextField(); // Campo para novo vídeo 
    
        // Campos específicos (Herói/Vilão)
        JTextField txtSpecificField;
        if (character instanceof SuperHero) {
            txtSpecificField = new JTextField(((SuperHero) character).getUniverse());
        } else {
            txtSpecificField = new JTextField(String.valueOf(((SuperVillain) character).getThreatLevel()));
        }
    
        // Adicionar campos ao painel
        addFormField(formPanel, "Nome:", txtName);
        addFormField(formPanel, "Descrição:", new JScrollPane(txtDescription));
        addFormField(formPanel, "Poderes:", new JScrollPane(txtPowers));
        addFormField(formPanel, "Grupo:", txtTeam);
        addFormField(formPanel, "Habilidades:", new JScrollPane(txtSkills));
        addFormField(formPanel, (character instanceof SuperHero) ? "Universo:" : "Nível de Ameaça:", txtSpecificField);
    
        // --- Botões de Ação ---
        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Cancelar");
    
        btnSave.addActionListener(e -> {
            // Coletar dados atualizados
            Map<String, String> updatedData = new HashMap<>();
            updatedData.put("id", String.valueOf(character.getId())); 
            updatedData.put("name", txtName.getText());
            updatedData.put("description", txtDescription.getText());
            updatedData.put("powers", txtPowers.getText());
            updatedData.put("team", txtTeam.getText());
            updatedData.put("skills", txtSkills.getText());
            updatedData.put("specificField", txtSpecificField.getText());
            updatedData.put("imagePath", txtImagePath.getText()); 
            updatedData.put("videoPath", txtVideoPath.getText()); 
    
            try {
                CharacterController controller = new CharacterController();
                controller.updateCharacter(character, updatedData); // Chama o Controller
                editDialog.dispose();
                refreshAlbum(); // Atualiza a lista
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, "Erro: " + ex.getMessage(), "Falha na Edição", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        btnCancel.addActionListener(e -> editDialog.dispose());
    
        // --- Montagem Final ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        editDialog.add(formPanel, BorderLayout.CENTER);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);
        editDialog.setLocationRelativeTo(this);
        editDialog.setVisible(true);
    }
    
    // Getter para o painel do álbum

    // Usada para a seleção de campos especificos (Heroi ou Vilão?)
    private String getSelectedTabType() {
        if (txtUniverse.getText().isEmpty() && txtThreatLevel.getText().isEmpty()) {
            throw new IllegalArgumentException("Selecione uma aba: Herói OU Vilão!");
        }
        return (!txtUniverse.getText().isEmpty()) ? "HERO" : "VILLAIN";
    }
    // Método para obter os dados do formulário
    public Map<String, String> getFormData() {
        Map<String, String> data = new HashMap<>();
        data.put("name", txtName.getText());
        data.put("description", txtDescription.getText());
        data.put("powers", txtPowers.getText());
        data.put("team", txtTeam.getText());
        data.put("skills", txtSkills.getText());
        data.put("imagePath", txtImagePath.getText());
        data.put("videoPath", txtVideoPath.getText());
        data.put("universe", txtUniverse.getText());         
        data.put("threatLevel", txtThreatLevel.getText());   
        return data;
    }

}