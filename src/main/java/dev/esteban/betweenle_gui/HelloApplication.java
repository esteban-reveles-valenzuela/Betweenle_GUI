package dev.esteban.betweenle_gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import dev.esteban.model.Juego;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class HelloApplication extends Application
{
    private Juego juego;
    private Stage stage;

    private String idiomaElegido = "es";

    private int longitudElegida = 5;
    private int intentosElegidos = 10;

    private StringBuilder intentoActual = new StringBuilder();

    private Label lblIntentos;
    private Label lblPorcentajeSup;
    private Label lblPorcentajeInf;

    private HBox rowLimiteSup;
    private HBox rowIntentoActual;
    private HBox rowLimiteInf;

    private Label lblLetrasUsadas;
    private HBox panelMiniAlfabeto;
    private VBox panelHistorial;

    private GridPane panelTeclado;

    private HashMap<String, String> textosEs = new HashMap<>();
    private HashMap<String, String> textosEn = new HashMap<>();
    private HashMap<String, String> lang;

    @Override
    public void init()
    {
        juego = new Juego();
        inicializarTextosUI();
    }

    @Override
    public void start(Stage primaryStage)
    {
        this.stage = primaryStage;
        stage.setTitle("Betweenle");
        mostrarPantallaIdioma();
    }

    private void inicializarTextosUI()
    {
        textosEs.put("regresar_idioma", "⬅ Idioma");
        textosEs.put("dificultad", "Dificultad / Longitud:");
        textosEs.put("intentos", "Número de Intentos:");
        textosEs.put("facil", "Fácil (5 letras)");
        textosEs.put("intermedio", "Intermedio (6 letras)");
        textosEs.put("dificil", "Difícil (7+ letras)");
        textosEs.put("jugar", "EMPEZAR JUEGO");
        textosEs.put("error_num", "Por favor ingresa un número válido mayor o igual a 7.");
        textosEs.put("error_dicc", "No existen palabras con esa longitud en el diccionario actual.");
        textosEs.put("ganaste", "¡Ganaste!");
        textosEs.put("perdiste", "Perdiste. Se agotaron los intentos.");
        textosEs.put("rendirse", "Te has rendido.");
        textosEs.put("secreta_era", "La palabra secreta era: ");
        textosEs.put("msg_agregar", "Esa palabra no está en el diccionario. ¿Quieres agregarla?");
        textosEs.put("intento", "INTENTO");
        textosEs.put("salir", "Salir");
        textosEs.put("pistas", "Pistas");
        textosEs.put("pista1", "Recorrer 1% límite de arriba");
        textosEs.put("pista2", "Recorrer 1% límite de abajo");
        textosEs.put("pista3", "Letra inicial");
        textosEs.put("sin_intentos", "Sin intentos");
        textosEs.put("advertencia", "Advertencia");
        textosEs.put("fin", "FIN DEL JUEGO");

        textosEn.put("regresar_idioma", "⬅ Language");
        textosEn.put("dificultad", "Difficulty / Length:");
        textosEn.put("intentos", "Number of Attempts:");
        textosEn.put("facil", "Easy (5 letters)");
        textosEn.put("intermedio", "Medium (6 letters)");
        textosEn.put("dificil", "Hard (7+ letters)");
        textosEn.put("jugar", "START GAME");
        textosEn.put("error_num", "Please enter a valid number greater than or equal to 7.");
        textosEn.put("error_dicc", "There are no words with that length in the current dictionary.");
        textosEn.put("ganaste", "You Won!");
        textosEn.put("perdiste", "You Lost. No attempts left.");
        textosEn.put("rendirse", "You gave up.");
        textosEn.put("secreta_era", "The secret word was: ");
        textosEn.put("msg_agregar", "That word is not in the dictionary. Do you want to add it?");
        textosEn.put("intento", "GUESS");
        textosEn.put("salir", "Exit");
        textosEn.put("pistas", "Hints");
        textosEn.put("pista1", "Move upper limit 1%");
        textosEn.put("pista2", "Move lower limit 1%");
        textosEn.put("pista3", "First letter");
        textosEn.put("sin_intentos", "No attemps");
        textosEn.put("advertencia", "Warning");
        textosEn.put("fin", "GAME OVER");
    }

    private void mostrarPantallaIdioma()
    {
        VBox root = new VBox(25);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #eeeeee;");

        Label lblTitulo = new Label("BETWEENLE");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 38));
        lblTitulo.setTextFill(Color.rgb(0, 0, 0));

        Label lblIdioma = new Label("Idioma / Language");
        lblIdioma.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        lblIdioma.setTextFill(Color.rgb(85, 85, 85));

        VBox boxBotones = new VBox(12);
        boxBotones.setAlignment(Pos.CENTER);

        Button btnEs = new Button("Español");
        Button btnEn = new Button("English");

        String estiloIdioma =
            "-fx-background-color: #00a2e8;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-pref-width: 180;" +
            "-fx-pref-height: 42;" +
            "-fx-background-radius: 4;" +
            "-fx-cursor: hand;";

        btnEs.setStyle(estiloIdioma);
        btnEn.setStyle(estiloIdioma);

        btnEs.setOnAction(e ->
        {
            idiomaElegido = "es";
            lang = textosEs;
            mostrarPantallaConfiguracion();
        });

        btnEn.setOnAction(e ->
        {
            idiomaElegido = "en";
            lang = textosEn;
            mostrarPantallaConfiguracion();
        });

        boxBotones.getChildren().addAll(btnEs, btnEn);
        root.getChildren().addAll(lblTitulo, lblIdioma, boxBotones);

        Scene scene = new Scene(root, 900, 750);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarPantallaConfiguracion()
    {
        VBox root = new VBox(40);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #eeeeee;");

        Button btnVolver = new Button(lang.get("regresar_idioma"));

        btnVolver.setStyle
        (
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #00a2e8;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 14px;" +
            "-fx-cursor: hand;"
        );

        btnVolver.setOnAction(e -> mostrarPantallaIdioma());
        btnVolver.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label("BETWEENLE");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        lblTitulo.setTextFill(Color.rgb(0, 0, 0));

        longitudElegida = 5;
        intentosElegidos = 10;

        Label lblDif = new Label(lang.get("dificultad"));
        lblDif.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        VBox boxDif = new VBox(8);
        boxDif.setAlignment(Pos.CENTER);

        ShapeButton btnFacil = new ShapeButton(lang.get("facil"), true, false);
        ShapeButton btnInter = new ShapeButton(lang.get("intermedio"), false, false);
        ShapeButton btnDificil = new ShapeButton(lang.get("dificil"), false, false);

        btnFacil.setOnAction(e ->
        {
            if(btnFacil.isActivo())
            {
                return;
            }

            longitudElegida = 5;
            btnFacil.setActivo(true);
            btnInter.setActivo(false);
            btnDificil.setActivo(false);
        });

        btnInter.setOnAction(e ->
        {
            if(btnInter.isActivo())
            {
                return;
            }

            longitudElegida = 6;
            btnFacil.setActivo(false);
            btnInter.setActivo(true);
            btnDificil.setActivo(false);
        });

        btnDificil.setOnAction(e ->
        {
            if(btnDificil.isActivo())
            {
                return;
            }

            longitudElegida = -1;
            btnFacil.setActivo(false);
            btnInter.setActivo(false);
            btnDificil.setActivo(true);
        });

        boxDif.getChildren().addAll(btnFacil, btnInter, btnDificil);

        Label lblInt = new Label(lang.get("intentos"));
        lblInt.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        HBox boxInt = new HBox(8);
        boxInt.setAlignment(Pos.CENTER);

        ShapeButton btn10 = new ShapeButton("10", true, true);
        ShapeButton btn12 = new ShapeButton("12", false, true);
        ShapeButton btn14 = new ShapeButton("14", false, true);

        btn10.setOnAction(e ->
        {
            if(btn10.isActivo())
            {
                return;
            }

            intentosElegidos = 10;
            btn10.setActivo(true);
            btn12.setActivo(false);
            btn14.setActivo(false);
        });

        btn12.setOnAction(e ->
        {
            if(btn12.isActivo())
            {
                return;
            }

            intentosElegidos = 12;
            btn10.setActivo(false);
            btn12.setActivo(true);
            btn14.setActivo(false);
        });

        btn14.setOnAction(e ->
        {
            if(btn14.isActivo())
            {
                return;
            }

            intentosElegidos = 14;
            btn10.setActivo(false);
            btn12.setActivo(false);
            btn14.setActivo(true);
        });

        boxInt.getChildren().addAll(btn10, btn12, btn14);
        Button btnJugar = new ImageButton("", "/jugar.png");

        btnJugar.setStyle
        (
            "-fx-background-color: #00a2e8;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 10 25;" +
            "-fx-background-radius: 4;" +
            "-fx-cursor: hand;"
        );

        btnJugar.setOnAction(e ->
        {
            if (longitudElegida == -1)
            {
                TextInputDialog inputDialog = new TextInputDialog("7");
                inputDialog.setTitle("Betweenle");
                inputDialog.setHeaderText(null);
                inputDialog.setContentText(lang.get("error_num"));
                Optional<String> result = inputDialog.showAndWait();

                if (result.isPresent())
                {
                    try
                    {
                        int valor = Integer.parseInt(result.get().trim());
                        if (valor >= 7) longitudElegida = valor; else return;
                    }
                    catch (NumberFormatException ex)
                    {
                        return;
                    }
                }
                else
                {
                    return;
                }
            }
            if (juego.configurar(idiomaElegido, longitudElegida, intentosElegidos))
            {
                mostrarPantallaJuego();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, lang.get("error_dicc"));
                alert.showAndWait();

                if (btnDificil.isActivo())
                {
                    longitudElegida = -1;
                }
            }
        });

        root.getChildren().addAll
        (
            btnVolver,
            lblTitulo,
            lblDif,
            boxDif,
            lblInt,
            boxInt,
            btnJugar
        );

        Scene scene = new Scene(root, 900, 750);
        stage.setResizable(false);
        stage.setScene(scene);
    }

    private void mostrarPantallaJuego()
    {
        intentoActual.setLength(0);

        VBox root = new VBox(15);
        root.setPadding(new Insets(15, 25, 15, 25));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #eeeeee;");

        HBox barraSuperior = new HBox();
        barraSuperior.setAlignment(Pos.CENTER);

        Button btnSalir = new ImageButton("", "/casa.png");

        btnSalir.setStyle
        (
            "-fx-background-color: transparent;" +
            "-fx-font-size: 24px;" +
            "-fx-cursor: hand;"
        );

        btnSalir.setFocusTraversable(false);

        btnSalir.setOnAction(e ->
        {
            mostrarPantallaIdioma();
        });

        Button btnPista = new ImageButton("", "/foco.png");

        btnPista.setStyle
        (
            "-fx-background-color: transparent;" +
            "-fx-font-size: 24px;" +
            "-fx-cursor: hand;"
        );

        btnPista.setFocusTraversable(false);

        btnPista.setOnAction(e ->
        {
            mostrarMenuPistas();
        });

        Label lblTituloJuego = new Label("BETWEENLE");

        lblTituloJuego.setFont
        (
            Font.font("Arial", FontWeight.BOLD, 28)
        );

        lblTituloJuego.setMaxWidth(Double.MAX_VALUE);
        lblTituloJuego.setAlignment(Pos.CENTER);

        HBox.setHgrow(lblTituloJuego, Priority.ALWAYS);

        barraSuperior.getChildren().addAll
        (
            btnSalir,
            lblTituloJuego,
            btnPista
        );

        Separator divisor = new Separator();
        VBox boxIntentos = new VBox(2);
        Label lblGuessTitle = new Label(lang.get("intento"));

        lblGuessTitle.setFont
        (
            Font.font("Arial", FontWeight.BOLD, 11)
        );

        lblIntentos = new Label();

        lblIntentos.setFont
        (
            Font.font("Arial", FontWeight.BOLD, 18)
        );

        boxIntentos.getChildren().addAll(lblGuessTitle, lblIntentos);
        GridPane tableroCentral = new GridPane();

        tableroCentral.setAlignment(Pos.CENTER);
        tableroCentral.setHgap(15);
        tableroCentral.setVgap(15);

        VBox ejePorcentajes = new VBox();
        ejePorcentajes.setAlignment(Pos.CENTER);

        lblPorcentajeSup = new Label("0");
        lblPorcentajeSup.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblPorcentajeSup.setTextFill(Color.rgb(255, 255, 255));
        lblPorcentajeSup.setAlignment(Pos.CENTER);
        lblPorcentajeSup.setPrefSize(40, 30);
        lblPorcentajeSup.setStyle("-fx-background-color: #00a2e8; -fx-background-radius: 5;");

        Rectangle linea = new Rectangle(4,75);
        linea.setFill(Color.rgb(0, 162, 232));

        StackPane contenedor = new StackPane();
        contenedor.setAlignment(Pos.TOP_CENTER);
        contenedor.getChildren().addAll(linea);

        lblPorcentajeInf = new Label("100");
        lblPorcentajeInf.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblPorcentajeInf.setTextFill(Color.rgb(255, 255, 255));
        lblPorcentajeInf.setAlignment(Pos.CENTER);
        lblPorcentajeInf.setPrefSize(40, 30);
        lblPorcentajeInf.setStyle("-fx-background-color: #00a2e8; -fx-background-radius: 5;");

        ejePorcentajes.getChildren().addAll
        (
            lblPorcentajeSup,
            contenedor,
            lblPorcentajeInf
        );

        rowLimiteSup = new HBox(5);
        rowIntentoActual = new HBox(5);
        rowLimiteInf = new HBox(5);

        VBox letras = new VBox(8);

        letras.getChildren().addAll
        (
            rowLimiteSup,
            rowIntentoActual,
            rowLimiteInf
        );

        tableroCentral.add(ejePorcentajes, 0, 0);
        tableroCentral.add(letras, 1, 0);

        panelMiniAlfabeto = new HBox(3);
        panelMiniAlfabeto.setAlignment(Pos.CENTER);

        panelHistorial = new VBox(5);
        panelHistorial.setPadding(new Insets(5));
        panelHistorial.setAlignment(Pos.TOP_CENTER);
        ScrollPane historialScroll = new ScrollPane(panelHistorial);
        historialScroll.setMaxWidth(300);
        historialScroll.setPrefHeight(140);

        lblLetrasUsadas = new Label();
        lblLetrasUsadas.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lblLetrasUsadas.setWrapText(true);
        lblLetrasUsadas.setMaxWidth(300);

        panelTeclado = new GridPane();
        panelTeclado.setAlignment(Pos.CENTER);
        panelTeclado.setHgap(6);
        panelTeclado.setVgap(6);

        root.getChildren().addAll
        (
            barraSuperior,
            divisor,
            boxIntentos,
            tableroCentral,
            panelMiniAlfabeto,
            historialScroll,
            lblLetrasUsadas,
            panelTeclado
        );

        actualizarTableroEstructural();
        actualizarAlfabeto();
        actualizarHistorial();
        actualizarLetrasUsadas();
        armarTecladoVirtual();

        Scene scene = new Scene(root, 900, 750);

        scene.setOnKeyPressed(e ->
        {
            if(e.getCode() == KeyCode.ENTER)
            {
                validarYEnviarIntento();
                reproducirSonido();
            }
            else if (e.getCode() == KeyCode.BACK_SPACE)
            {
                borrarCaracterAnterior();
                reproducirSonido();
            }
            else
            {
                String txt = e.getText().toLowerCase();

                if(txt.length()==1)
                {
                    char c = txt.charAt(0);

                    if ((c >= 'a' && c <= 'z') || (c == 'ñ' && idiomaElegido.equals("es")) ||
                        ("áéíóú".indexOf(c) != -1 && idiomaElegido.equals("es")))
                    {
                        recibirLetraTeclado(c);
                        reproducirSonido();
                    }
                }
            }
        });

        stage.setScene(scene);
    }

    private void actualizarTableroEstructural()
    {
        rowLimiteSup.getChildren().clear();
        rowIntentoActual.getChildren().clear();
        rowLimiteInf.getChildren().clear();
        String sup = juego.getLimiteSup();
        String inf = juego.getLimiteInf();
        String defaultSup = "a".repeat(longitudElegida);
        String defaultInf = "z".repeat(longitudElegida);
        boolean mostrarPorcentajes = (sup != null || inf != null);
        String txtSup = (sup == null) ? defaultSup : sup;
        String txtInf = (inf == null) ? defaultInf : inf;

        if (mostrarPorcentajes)
        {
            lblPorcentajeSup.setText
            (
                juego.getPorcentajeArriba() >= 10 ?
                    String.valueOf((int)juego.getPorcentajeArriba()) :
                    String.format("%.2f", juego.getPorcentajeArriba())
            );

            lblPorcentajeInf.setText
            (
                juego.getPorcentajeAbajo() >= 10 ?
                    String.valueOf((int)juego.getPorcentajeAbajo()) :
                    String.format("%.2f", juego.getPorcentajeAbajo())
            );
        }
        else
        {
            lblPorcentajeSup.setText("?");
            lblPorcentajeInf.setText("?");
        }

        for (int i = 0; i < longitudElegida; i ++)
        {
            rowLimiteSup.getChildren().add
            (crearCasillaLetra(String.valueOf(txtSup.charAt(i)).toUpperCase(), "#00a2e8", true));
            String caracterFila = "";

            if (i < intentoActual.length())
            {
                caracterFila = String.valueOf(intentoActual.charAt(i)).toUpperCase();
            }
            else if (i == intentoActual.length())
            {
                caracterFila = "•";
            }

            rowIntentoActual.getChildren().add
            (
                crearCasillaLetra(caracterFila, "#ffffff", false)
            );

            rowLimiteInf.getChildren().add
            (
                crearCasillaLetra(String.valueOf(txtInf.charAt(i)).toUpperCase(), "#00a2e8", true)
            );
        }

        lblIntentos.setText((juego.getIntentosHechos() + 1) + " / " + intentosElegidos);
    }

    private Label crearCasillaLetra(String letra, String colorFondo, boolean esLimite)
    {
        Label lbl = new Label(letra.isEmpty() ? " " : letra);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        lbl.setAlignment(Pos.CENTER);
        lbl.setPrefSize(46, 46);
        if (esLimite)
        {
            lbl.setStyle
            (
                "-fx-background-color: " + colorFondo + ";" +
                "-fx-text-fill: white;" +
                "-fx-border-color: #ffffff;" +
                "-fx-border-width: 0.5;"
            );
        }
        else
        {
            if (letra.equals("•"))
            {
                lbl.setStyle
                (
                    "-fx-background-color: #e0e0e0;" +
                    "-fx-text-fill: #666666;" +
                    "-fx-border-color: #aaaaaa;" +
                    "-fx-border-width: 1.5;"
                );
            }
            else if (!letra.trim().isEmpty())
            {
                lbl.setStyle
                (
                    "-fx-background-color: #ffffff;" +
                    "-fx-text-fill: #000000;" +
                    "-fx-border-color: #444444;" +
                    "-fx-border-width: 1.5;"
                );
            }
            else
            {
                lbl.setStyle
                (
                    "-fx-background-color: #f8f8f8;" +
                    "-fx-border-color: #cccccc;" +
                    "-fx-border-width: 1;"
                );
            }
        }
        return lbl;
    }

    private void actualizarAlfabeto()
    {
        panelMiniAlfabeto.getChildren().clear();
        ArrayList<Character> alfabeto = generarAlfabetoRonda();
        int indiceEdicion = intentoActual.length();

        String sup = juego.getLimiteSup();
        String inf = juego.getLimiteInf();

        String txtSup = (sup == null) ? "a".repeat(longitudElegida) : sup.toLowerCase();
        String txtInf = (inf == null) ? "z".repeat(longitudElegida) : inf.toLowerCase();

        Collator collator = Collator.getInstance(new Locale("es", "MX"));
        collator.setStrength(Collator.SECONDARY);

        for (char c : alfabeto)
        {
            boolean estaHabilitada = true;

            if (indiceEdicion < longitudElegida)
            {
                String cadenaSimulada = intentoActual.toString() + Character.toLowerCase(c);
                String subCadenaSup = txtSup.substring(0, indiceEdicion + 1);
                String subCadenaInf = txtInf.substring(0, indiceEdicion + 1);

                estaHabilitada = collator.compare(cadenaSimulada, subCadenaSup) >= 0 &&
                    collator.compare(cadenaSimulada, subCadenaInf) <= 0;
            }

            Label lblLetra = new Label(String.valueOf(c).toUpperCase());
            lblLetra.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            lblLetra.setPrefSize(16, 22);
            lblLetra.setAlignment(Pos.CENTER);

            if (estaHabilitada)
            {
                lblLetra.setTextFill(Color.rgb(84, 71, 65));

                lblLetra.setStyle
                (
                    "-fx-background-color: #e5dfda;" +
                    "-fx-background-radius: 30;"
                );
            }
            else
            {
                lblLetra.setTextFill(Color.rgb(204, 204, 204));
                lblLetra.setStyle("-fx-background-color: transparent;");
            }

            panelMiniAlfabeto.getChildren().add(lblLetra);
        }
    }

    private ArrayList<Character> generarAlfabetoRonda()
    {
        ArrayList<Character> lista = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c ++)
        {
            lista.add(c);
            if (c == 'n' && "es".equals(idiomaElegido))
            {
                lista.add('ñ');
            }
        }
        return lista;
    }

    private void recibirLetraTeclado(char letra)
    {
        if (intentoActual.length() < longitudElegida)
        {
            intentoActual.append(Character.toLowerCase(letra));
            actualizarTableroEstructural();
            actualizarAlfabeto();
        }
    }

    private void borrarCaracterAnterior()
    {
        if (intentoActual.length() > 0)
        {
            intentoActual.setLength(intentoActual.length() - 1);
            actualizarTableroEstructural();
            actualizarAlfabeto();
        }
    }

    private void validarYEnviarIntento()
    {
        if(intentoActual.length() != longitudElegida)
        {
            return;
        }

        String resultado = juego.intentar(intentoActual.toString());

        if(Juego.NO_EN_DICCIONARIO.equals(resultado))
        {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setHeaderText(null);
            confirmacion.setContentText(lang.get("msg_agregar"));
            Optional<ButtonType> opc = confirmacion.showAndWait();

            if (opc.isPresent() && opc.get() == ButtonType.OK)
            {
                juego.agregarPalabra(intentoActual.toString());
                resultado = juego.intentar(intentoActual.toString());
            }
            else
            {
                intentoActual.setLength(0);
                actualizarTableroEstructural();
                actualizarAlfabeto();
                actualizarLetrasUsadas();
                return;
            }
        }

        boolean errorLimite = resultado.startsWith("La palabra debe estar") ||
            resultado.startsWith("The word must come");

        if(errorLimite)
        {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setHeaderText(null);
            alerta.setTitle(lang.get("advertencia"));
            alerta.setContentText(resultado);
            alerta.showAndWait();
            return;
        }

        actualizarHistorial();
        if(juego.termino())
        {
            Alert fin = new Alert(Alert.AlertType.INFORMATION);
            fin.setTitle(lang.get("fin"));
            fin.setHeaderText
            (
                juego.gano() ?
                lang.get("ganaste") :
                lang.get("perdiste")
            );

            fin.setContentText
            (
                lang.get("secreta_era") + juego.getPalabraSecreta().toUpperCase()
            );

            fin.showAndWait();
            mostrarPantallaIdioma();
        }
        else
        {
            intentoActual.setLength(0);
            actualizarTableroEstructural();
            actualizarAlfabeto();
            actualizarLetrasUsadas();
        }
    }

    private void armarTecladoVirtual()
    {
        panelTeclado.getChildren().clear();

        String[][] esquema = "es".equals(idiomaElegido) ?
            new String[][]
            {
                {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
                {"A", "S", "D", "F", "G", "H", "J", "K", "L", "Ñ"},
                {"↵", "Z", "X", "C", "V", "B", "N", "M", "⌫"},
                {"Á", "É", "Í", "Ó", "Ú"}
            } :
            new String[][]
            {
                {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
                {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
                {"↵", "Z", "X", "C", "V", "B", "N", "M", "⌫"}
            };

        for (int fil = 0; fil < esquema.length; fil ++)
        {
            for (int col = 0; col < esquema[fil].length; col ++)
            {
                String textoCelda = esquema[fil][col];
                Button btnTeclado;

                if (textoCelda.equals("↵"))
                {
                    btnTeclado = new SoundButton("↵");
                    btnTeclado.setOnAction(e -> validarYEnviarIntento());
                }
                else if (textoCelda.equals("⌫"))
                {
                    btnTeclado = new SoundButton("⌫");
                    btnTeclado.setOnAction(e -> borrarCaracterAnterior());
                }
                else
                {
                    btnTeclado = new SoundButton(textoCelda);
                    char charLetra = textoCelda.toLowerCase().charAt(0);
                    btnTeclado.setOnAction(e -> recibirLetraTeclado(charLetra));
                }

                btnTeclado.setPrefWidth(45);
                btnTeclado.setPrefHeight(45);
                btnTeclado.setFocusTraversable(false);

                int colPos = (fil == 3) ? col + 2 : col;
                panelTeclado.add(btnTeclado, colPos, fil);
            }
        }
    }

    private void actualizarLetrasUsadas()
    {
        HashSet<Character> usadas = juego.getLetrasUsadas();

        if(usadas.isEmpty())
        {
            lblLetrasUsadas.setText
            (
                idiomaElegido.equals("es") ?
                    "Letras usadas: Ninguna" :
                    "Used letters: None"
            );

            return;
        }

        Collator col = Collator.getInstance(new Locale("es", "MX"));
        col.setStrength(Collator.SECONDARY);
        Comparator comparador = (a, b) -> col.compare(a.toString(), b.toString());

        ArrayList<Character> ordenadas = (ArrayList) usadas
            .stream()
            .sorted(comparador)
            .collect(Collectors.toCollection(ArrayList :: new));

        StringBuilder txt = new StringBuilder();

        txt.append
        (
            idiomaElegido.equals("es") ?
                "Letras usadas: " :
                "Used letters: "
        );

        for(char c : ordenadas)
        {
            txt.append(Character.toUpperCase(c));
            txt.append(" ");
        }

        lblLetrasUsadas.setText(txt.toString());
    }

    private void actualizarHistorial()
    {
        panelHistorial.getChildren().clear();
        ArrayList<String> historial = juego.getHistorial();

        if(historial.isEmpty())
        {
            Label vacio = new Label(lang.get("sin_intentos"));
            vacio.setTextFill(Color.rgb(128, 128, 128));
            panelHistorial.getChildren().add(vacio);
            return;
        }

        for(String intento : historial)
        {
            Label lbl = new Label(intento);
            lbl.setMaxWidth(280);

            lbl.setStyle
            (
                "-fx-background-color:#dddddd;" +
                "-fx-padding:5;" +
                "-fx-background-radius:4;"
            );

            panelHistorial.getChildren().add(lbl);
        }
    }

    private void mostrarMenuPistas()
    {
        String title = lang.get("pistas");
        String op1 = lang.get("pista1");
        String op2 = lang.get("pista2");
        String op3 = lang.get("pista3");

        ChoiceDialog<String> dialog = new ChoiceDialog<>
        (
            op1, Arrays.asList(op1, op2, op3)
        );

        dialog.setTitle(title);
        dialog.setHeaderText(null);
        Optional<String> op = dialog.showAndWait();

        if(op.isEmpty())
        {
            return;
        }

        int tipo;

        switch(op.get())
        {
            case "Recorrer 1% límite de arriba":
                tipo = 1;
            break;
            case "Move upper limit 1%":
                tipo = 1;
            break;
            case "Recorrer 1% límite de abajo":
                tipo = 2;
            break;
            case "Move lower limit 1%":
                tipo = 2;
                break;
            default:
                tipo = 3;
            break;
        }

        String pista = juego.pedirPista(tipo);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(lang.get("pistas"));
        alerta.setHeaderText(null);
        alerta.setContentText(pista);
        alerta.showAndWait();

        actualizarTableroEstructural();
        actualizarAlfabeto();
    }

    public void reproducirSonido()
    {
        try
        {
            InputStream is = getClass().getResourceAsStream("/clic.wav");

            if (is == null)
            {
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }
        catch (Exception ex)
        {
            System.err.println("No se pudo reproducir el sonido: " + ex.getMessage());
        }
    }
}

class ShapeButton extends Button
{
    private boolean activo;
    public ShapeButton(String texto, boolean activoInicial, boolean esCircular)
    {
        super(texto);
        this.activo = activoInicial;

        if (esCircular)
        {
            setShape(new Circle(20));
            setMinSize(44, 44); setMaxSize(44, 44);
        }

        else setPrefSize(180, 40);
        renderizarEstiloFijo();
    }

    public void setActivo(boolean est)
    {
        this.activo = est;
        renderizarEstiloFijo();
    }

    public boolean isActivo()
    {
        return this.activo;
    }

    private void renderizarEstiloFijo()
    {
        setStyle
        (
            activo ?
                "-fx-background-color: #00a2e8;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;" +
                "-fx-background-radius: 4;" :

                "-fx-background-color: #dddddd;" +
                "-fx-text-fill: #555555;" +
                "-fx-cursor: hand;" +
                "-fx-background-radius: 4;"
        );
    }
}

class SoundButton extends Button
{
    public SoundButton(String texto)
    {
        super(texto);

        setStyle
        (
            "-fx-background-color: #cccccc;" +
            "-fx-text-fill: #111111;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;"
        );

        setOnMouseEntered(e ->
        {
            setStyle
            (
                "-fx-background-color: #b5b5b5;" +
                "-fx-text-fill: #000000;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 6;"
            );
        });

        setOnMouseExited(e ->
        {
            setStyle
            (
                "-fx-background-color: #cccccc;" +
                "-fx-text-fill: #111111;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 6;"
            );
        });

        setOnMousePressed(e ->
        {
            try
            {
                InputStream is = getClass().getResourceAsStream("/clic.wav");

                if (is == null)
                {
                    return;
                }

                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            }
            catch (Exception ex)
            {
                System.err.println("No se pudo reproducir el sonido: " + ex.getMessage());
            }
        });
    }
}

class ImageButton extends Button
{
    public ImageButton(String text, String ruta)
    {
        super(text);
        InputStream is = getClass().getResourceAsStream(ruta);

        if (is != null)
        {
            Image img = new Image(is);
            ImageView view = new ImageView(img);

            view.setFitHeight(50);
            view.setFitWidth(50);
            view.setPreserveRatio(true);

            this.setGraphic(view);
        }
        else
        {
            System.err.println("Advertencia: No se pudo cargar la imagen en: " + ruta);
        }
    }
}