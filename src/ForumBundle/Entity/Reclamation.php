<?php

namespace ForumBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Reclamation
 *
 * @ORM\Table(name="reclamation")
 * @ORM\Entity(repositoryClass="ForumBundle\Repository\ReclamationRepository")
 */
class Reclamation
{
    /**
     * @ORM\ManyToOne(targetEntity="UserBundle\Entity\User")
     * @ORM\JoinColumn(name="userId", referencedColumnName="id")
     */
    public $user;

    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="descriptionRec", type="text")
     */
    private $descriptionRec;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateRec", type="date")
     */
    private $dateRec;

    /**
     * @var string
     *
     * @ORM\Column(name="title", type="string", length=255)
     */
    private $title;

    /**
     * @var boolean
     *
     * @ORM\Column(name="handled", type="boolean", length=255)
     */
    private $handled;


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
     * Set dateRec
     *
     * @param \DateTime $dateRec
     *
     * @return Reclamation
     */
    public function setDateRec($dateRec)
    {
        $this->dateRec = $dateRec;

        return $this;
    }

    /**
     * Get dateRec
     *
     * @return \DateTime
     */
    public function getDateRec()
    {
        return $this->dateRec;
    }

    /**
     * @return string
     */
    public function getDescriptionRec()
    {
        return $this->descriptionRec;
    }

    /**
     * @param string $descriptionRec
     */
    public function setDescriptionRec($descriptionRec)
    {
        $this->descriptionRec = $descriptionRec;
    }

    /**
     * @return string
     */
    public function getTitle()
    {
        return $this->title;
    }

    /**
     * @param string $title
     */
    public function setTitle($title)
    {
        $this->title = $title;
    }

    /**
     * @return bool
     */
    public function isHandled()
    {
        return $this->handled;
    }

    /**
     * @param bool $handled
     */
    public function setHandled($handled)
    {
        $this->handled = $handled;
    }


}

