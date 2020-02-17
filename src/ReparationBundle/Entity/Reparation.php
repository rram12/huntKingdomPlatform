<?php

namespace ReparationBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Reparation
 *
 * @ORM\Table(name="reparation")
 * @ORM\Entity(repositoryClass="ReparationBundle\Repository\ReparationRepository")
 */
class Reparation
{


    /**
     * @ORM\ManyToOne(targetEntity="UserBundle\Entity\User")
     * @ORM\JoinColumn(name="userId", referencedColumnName="id")
     */
    private $user;

    /**
     * @return mixed
     */
    public function getReparateur()
    {
        return $this->user;
    }

    /**
     * @param mixed $reparateur
     */
    public function setReparateur($reparateur)
    {
        $this->user = $reparateur;
    }

    /**
     * @ORM\ManyToOne(targetEntity="Piecesdefectueuses")
     * @ORM\JoinColumn(name="Piecesdefectueuses_id", referencedColumnName="id")
     */
    private $piecedefectueuse;


    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var float
     *
     * @ORM\Column(name="prixRep", type="float")
     */
    private $prixRep;

    /**
     * @var string
     *
     * @ORM\Column(name="etat", type="string", length=255)
     */
    private $etat;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="duree", type="time")
     */
    private $duree;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="text")
     */
    private $description;

    /**
     * @return mixed
     */
    public function getPiecedefectueuse()
    {
        return $this->piecedefectueuse;
    }

    /**
     * @param mixed $piecedefectueuse
     */
    public function setPiecedefectueuse($piecedefectueuse)
    {
        $this->piecedefectueuse = $piecedefectueuse;
    }


    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set prixRep
     *
     * @param float $prixRep
     *
     * @return Reparation
     */
    public function setPrixRep($prixRep)
    {
        $this->prixRep = $prixRep;

        return $this;
    }

    /**
     * Get prixRep
     *
     * @return float
     */
    public function getPrixRep()
    {
        return $this->prixRep;
    }

    /**
     * Set etat
     *
     * @param string $etat
     *
     * @return Reparation
     */
    public function setEtat($etat)
    {
        $this->etat = $etat;

        return $this;
    }

    /**
     * Get etat
     *
     * @return string
     */
    public function getEtat()
    {
        return $this->etat;
    }

    /**
     * Set duree
     *
     * @param \DateTime $duree
     *
     * @return Reparation
     */
    public function setDuree($duree)
    {
        $this->duree = $duree;

        return $this;
    }

    /**
     * Get duree
     *
     * @return \DateTime
     */
    public function getDuree()
    {
        return $this->duree;
    }

    /**
     * Set description
     *
     * @param string $description
     *
     * @return Reparation
     */
    public function setDescription($description)
    {
        $this->description = $description;

        return $this;
    }

    /**
     * Get description
     *
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }
}

