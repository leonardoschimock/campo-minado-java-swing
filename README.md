# 💣 Campo Minado

> Projeto acadêmico desenvolvido para a disciplina de **Programação Orientada a Objetos**, utilizando Java e Java Swing.

---

## 📚 Informações do Projeto

| Informação | Detalhes |
|---|---|
| **Projeto** | Campo Minado |
| **Disciplina** | Programação Orientada a Objetos |
| **Linguagem** | Java |
| **Interface gráfica** | Java Swing |
| **Paradigma** | Programação Orientada a Objetos |
| **IDE utilizada** | IntelliJ IDEA |
| **Tema visual** | Windows clássico |

---

## 🎮 Sobre o Projeto

O **Campo Minado** é um jogo de lógica no qual o jogador deve revelar todas as células que não possuem minas.

Cada célula pode conter uma mina ou indicar, através de um número, quantas minas existem nas oito posições adjacentes.

O objetivo é revelar todas as células seguras sem clicar em uma mina.

O projeto busca reproduzir não apenas a mecânica tradicional do jogo, mas também parte da experiência visual do **Campo Minado clássico do Windows**, utilizando elementos como:

- Botões com efeito de relevo 3D;
- Interface em tons de cinza;
- Contador de minas;
- Contador de tempo;
- Botão de reinício com rosto;
- Bandeiras para marcar possíveis minas;
- Diferentes níveis de dificuldade;
- Visual inspirado nos aplicativos clássicos do Windows.

---

## 🕹️ Funcionalidades

### 🔹 Novo jogo

Permite iniciar uma nova partida mantendo o nível de dificuldade selecionado.

### 🔹 Níveis de dificuldade

O jogo possui três níveis:

#### 🟢 Beginner

- Tabuleiro: **9 × 9**
- Minas: **10**
- Células maiores para proporcionar uma janela mais confortável.

#### 🟡 Intermediate

- Tabuleiro: **16 × 16**
- Minas: **40**

#### 🔴 Expert

- Tabuleiro: **16 × 30**
- Minas: **99**

---

## 🖱️ Controles

| Ação | Comando |
|---|---|
| Revelar célula | Clique esquerdo |
| Colocar/remover bandeira | Clique direito |
| Reiniciar partida | Botão do rosto |
| Novo jogo | Menu `Game → New` |

---

## 💣 Funcionamento

Ao iniciar uma partida, o tabuleiro é preenchido com células fechadas.

As minas são distribuídas aleatoriamente após a primeira jogada. Isso garante que a primeira célula selecionada pelo jogador não contenha uma mina.

Após revelar uma célula:

- Se houver uma mina, o jogador perde;
- Se não houver minas próximas, as células vizinhas são reveladas automaticamente;
- Se existirem minas próximas, um número indica a quantidade de minas adjacentes.

O jogador pode utilizar as bandeiras para marcar células que acredita conterem minas.

A partida termina quando:

### ❌ Derrota

O jogador revela uma célula que contém uma mina.

### 🏆 Vitória

Todas as células que não possuem minas são reveladas.

---

## 🧩 Estrutura do Projeto

O projeto está organizado nas seguintes classes:

```text
src/
├── CampoMinado.java
├── Celula.java
├── IconesCampoMinado.java
├── JanelaCampoMinado.java
├── Main.java
└── imagens/
    ├── bomba.png
    ├── flag.png
    ├── rostoTriste.png
    ├── smile.png
    └── vitoria.png
```

---

## 🏗️ Classes

### `Main.java`

É o ponto de entrada da aplicação.

Responsável por iniciar a interface gráfica do jogo utilizando a `Event Dispatch Thread` do Swing.

```java
SwingUtilities.invokeLater(() -> {
    new JanelaCampoMinado();
});
```

### `CampoMinado.java`

Representa a lógica principal do jogo.

É responsável por:

- Criar o tabuleiro;
- Distribuir as minas;
- Calcular as minas vizinhas;
- Revelar células;
- Revelar células vizinhas automaticamente;
- Gerenciar bandeiras;
- Verificar vitória;
- Detectar derrota;
- Controlar o estado da partida.

Essa classe concentra as principais regras do jogo, mantendo a lógica separada da interface gráfica.

### `Celula.java`

Representa uma célula individual do tabuleiro.

Cada objeto `Celula` possui informações como:

- Se contém uma mina;
- Se foi revelada;
- Se possui uma bandeira;
- Quantidade de minas vizinhas.

Exemplo:

```java
private boolean mina;
private boolean revelada;
private boolean bandeira;
private int minasVizinhas;
```

### `JanelaCampoMinado.java`

É responsável pela interface gráfica do jogo.

Utiliza componentes do **Java Swing** para criar:

- Janela principal;
- Menus;
- Tabuleiro;
- Botões;
- Contador de minas;
- Contador de tempo;
- Botão de reinício;
- Mensagens de vitória e derrota.

Também possui uma implementação personalizada dos botões para reproduzir o efeito visual de relevo característico das interfaces antigas do Windows.

### `IconesCampoMinado.java`

Responsável pelo carregamento e tratamento das imagens utilizadas no jogo.

Os principais elementos gráficos são:

- 😊 Rosto normal;
- 😞 Rosto de derrota;
- 😎 Rosto de vitória;
- 💣 Mina;
- 🚩 Bandeira.

A classe também realiza o tratamento das imagens para remover áreas transparentes desnecessárias e redimensioná-las mantendo o estilo pixelizado.

---

## 🧠 Conceitos de Programação Orientada a Objetos

O projeto foi desenvolvido com base em conceitos fundamentais de **Programação Orientada a Objetos**.

### Encapsulamento

Os atributos das classes são mantidos como `private`, sendo acessados através de métodos públicos.

Exemplo:

```java
private boolean mina;

public boolean temMina() {
    return mina;
}
```

Isso impede o acesso direto aos dados internos dos objetos e permite maior controle sobre seu estado.

### Abstração

Cada classe possui uma responsabilidade específica dentro do sistema.

Por exemplo:

```text
Celula
   ↓
Representa uma célula

CampoMinado
   ↓
Controla as regras do jogo

JanelaCampoMinado
   ↓
Controla a interface

IconesCampoMinado
   ↓
Controla os recursos gráficos
```

Essa divisão permite representar diferentes partes do problema através de objetos.

### Composição

O objeto `CampoMinado` possui várias instâncias de `Celula` para formar o tabuleiro.

```java
private Celula[][] tabuleiro;
```

Assim, o tabuleiro é composto por diversos objetos `Celula`.

### Responsabilidade das classes

O projeto procura manter uma separação entre **lógica do jogo** e **interface gráfica**.

A lógica principal não fica diretamente dentro dos botões da interface.

Por exemplo:

```java
campo.revelar(linha, coluna);
```

A interface solicita a ação ao objeto responsável pelo jogo, enquanto `CampoMinado` decide o que deve acontecer.

---

## 🎨 Interface

A interface foi desenvolvida utilizando **Java Swing** e recebeu modificações visuais para se aproximar do estilo clássico do Windows.

Entre os elementos utilizados estão:

- `JFrame`
- `JPanel`
- `JButton`
- `JLabel`
- `JMenuBar`
- `JMenu`
- `JMenuItem`
- `JOptionPane`
- `Timer`

Os botões das células também possuem uma implementação personalizada para reproduzir o efeito de relevo das interfaces gráficas antigas.

---

## ⏱️ Contador de Tempo

Durante a partida existe um contador responsável por registrar o tempo utilizado pelo jogador.

O contador é atualizado a cada segundo através de um `javax.swing.Timer`.

O tempo é limitado a:

```text
999 segundos
```

seguindo a característica tradicional do Campo Minado clássico.

---

## 🚩 Sistema de Bandeiras

O jogador pode marcar uma célula utilizando o botão direito do mouse.

As bandeiras são utilizadas para indicar células que o jogador acredita conterem minas.

O contador superior também é atualizado de acordo com a quantidade de bandeiras utilizadas.

---

## 💥 Sistema de Vitória e Derrota

Quando uma mina é revelada, o jogo é encerrado e as minas são reveladas.

O botão do rosto também é alterado para indicar a derrota.

Quando todas as células seguras são reveladas, o jogador vence e as minas restantes são automaticamente marcadas.

---

## 🛠️ Tecnologias Utilizadas

- **Java**
- **Java Swing**
- **AWT**
- **Programação Orientada a Objetos**
- **IntelliJ IDEA**
- **Git/GitHub**

---

## ▶️ Como Executar

### 1. Clonar o repositório

```bash
git clone URL_DO_REPOSITORIO
```

### 2. Abrir o projeto

Abra o projeto em uma IDE compatível com Java, como:

- IntelliJ IDEA;
- Eclipse;
- Apache NetBeans;
- Visual Studio Code com suporte ao Java.

### 3. Verificar as imagens

Certifique-se de que a pasta `imagens` esteja dentro de:

```text
src/imagens/
```

com os seguintes arquivos:

```text
bomba.png
flag.png
rostoTriste.png
smile.png
vitoria.png
```

### 4. Executar

Execute a classe:

```text
Main.java
```

A janela do Campo Minado será aberta automaticamente.

---

## 📁 Organização da Arquitetura

De maneira simplificada, o funcionamento do projeto pode ser representado da seguinte forma:

```text
                    ┌───────────────┐
                    │    Main.java  │
                    └───────┬───────┘
                            │
                            ▼
                ┌──────────────────────┐
                │ JanelaCampoMinado    │
                │ Interface gráfica    │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │    CampoMinado       │
                │   Regras do jogo     │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │       Celula         │
                │ Estado de cada célula│
                └──────────────────────┘

                ┌──────────────────────┐
                │ IconesCampoMinado    │
                │ Recursos gráficos    │
                └──────────────────────┘
```

---

## 🎓 Objetivo Acadêmico

O principal objetivo do projeto é aplicar, de maneira prática, os conceitos estudados na disciplina de **Programação Orientada a Objetos**.

A implementação do Campo Minado permite trabalhar conceitos como:

- Classes e objetos;
- Encapsulamento;
- Abstração;
- Composição;
- Métodos e atributos;
- Separação de responsabilidades;
- Estruturação de sistemas;
- Manipulação de eventos;
- Interfaces gráficas;
- Organização e reutilização de código.

Além disso, o projeto demonstra a utilização desses conceitos em uma aplicação interativa com interface gráfica.

---

## 👨‍💻 Autor

**Leonardo Schimock**

Projeto acadêmico desenvolvido para a disciplina de:

> **Programação Orientada a Objetos**

---

## 📜 Licença

Este projeto foi desenvolvido para fins **acadêmicos e educacionais**.
