package br.com.andrecouto.paypay.view.custom;

import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public interface HeaderInterface {

    /**
     * Define todos os textos do header
     *
     * @param firstName  texto de 'bem-vindo'
     * @param name       primeiro e último nome
     * @param am     am
     * @param shot    shot
     * @param lastAccess data do último acesso
     */
    void setAvatarText(String firstName, String name, String am, String shot, String lastAccess);

    /**
     * Define o texto do primeiro item de menu ('enviar foto' ou 'alterar foto')
     *
     * @param title texto do item de menu
     */
    void setAvatarClickText(String title);

    /**
     * Define o texto do item de menu 'avaliar aplicativo' que difere para personallite
     *
     * @param title texto do item de menu
     */
    void setEvaluateClickText(String title);

    /**
     * Obtem uma instancia do imageView da imagem do perfil do header fixo
     *
     * @return instancia do imageView da imagem do perfil
     */
    ImageView getAvatarImageView();

    /**
     * Obtem uma instancia do imageView da imagem do perfil do dialog do perfil do usuário
     *
     * @return instancia do imageView da imagem do perfil do dialog do perfil do usuário
     */
    ImageView getAvatarMenuProfileImageView();

    /**
     * Obtem a instancia do textView com as inicias do nome do usuário do header fixo
     *
     * @return instancia do textView com as inicias do nome do usuário do header fixo
     */
    TextView getAvatarFirstLettersTextView();

    /**
     * Obtem a instancia do textView com as inicias do nome do dialog do perfil do usuário
     *
     * @return instancia do textView com as inicias do nome do dialog do perfil do usuário
     */
    TextView getAvatarFirstLettersMenuProfileTextView();

    /**
     * Obtem instancia do menu de segundo nível (menu de 'segurança')
     *
     * @return instancia do menu de segundo nível
     */
   // MenuSecondLevelView getMenuSecondLevel();

    /**
     * Anima a abertura e fechamento do dialog de perfil de usuário
     *
     * @param animation animação
     */
    void setAnimateMenuProfile(Animation animation);

    /**
     * Anima o menu de segundo nível (menu de 'segurança')
     *
     * @param animation animação
     */
    void setAnimateMenuSecondLevel(Animation animation);

    /**
     * Define a visibilidade do menu de segundo nível
     *
     * @param visibility visibilidade por ser 'gone' ou 'visible'
     */
    void setMenuSecondLevelViewVisibility(int visibility);

    /**
     * Define a visibilidade do menu de segundo nível
     *
     * @param visibility visibilidade por ser 'gone' ou 'visible'
     */
    void setMenuSecondLevelVisibility(int visibility);

    /**
     * Define a visibilidade do dialog do Perfil do usuário
     *
     * @param visibility visibilidade por ser 'gone' ou 'visible'
     */
    void setMenuProfileVisibility(int visibility);

    /**
     * Define a visibilidade da animação do dialog do Perfil do usuário
     *
     * @param visibility visibilidade por ser 'gone' ou 'visible'
     */
    void setMenuProfileAnimateVisibility(int visibility);

    /**
     * Define os textos de acessibilidade do header fixo
     *
     * @param firstName texto
     * @param shot    shot
     * @param am   am
     */
    void setHeaderAccessibility(String firstName, String am, String shot);

    /**
     * Define o foco de acessibilidade na seta de fechar o menu perfil
     */
    void setArrowInMenuProfileAccessibilityFocus();

    /**
     * Define o foco no header
     */
    void setHeaderAccessibilityFocus();

    /**
     * Define o foco no header com tempo
     *
     * @param time define o tempo até o setar o foco
     */
    void setHeaderAccessibilityFocus(int time);

    /**
     * Retorna a view root do header
     *
     * @return view root do header
     */
    List<View> getViewsToDisableInAccessibility();
}
