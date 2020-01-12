package com.bassiuz.gameforcehub.Player;

import org.w3c.dom.Node;

import javax.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;

    @Column(name = "dci", unique=true)
    private String dci;

    public static Player findOrCreatePlayer(Node node, PlayerRepository playerRepository)
    {
        Player player = playerRepository.findByDci(node.getAttributes().getNamedItem("id").getNodeValue());
        if (player == null)
        {
            return new Player(node);
        }
        else
        {
            return player;
        }
    }

    public Player(Node node) {
        firstName = node.getAttributes().getNamedItem("first").getNodeValue();
        lastName = node.getAttributes().getNamedItem("last").getNodeValue();
        dci = node.getAttributes().getNamedItem("id").getNodeValue();
    }

    public Player(){}

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDci() {
        return dci;
    }

    public void setDci(String dci) {
        dci = dci;
    }

    @Override
    public String toString() {
        return "Player{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dci='" + dci + '\'' +
                '}';
    }
}
