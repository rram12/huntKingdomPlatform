<?php

namespace EventsBundle\Controller;

use EventsBundle\Entity\Competition;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class EventController extends Controller
{
    public function affichercompetitionAction() {
        $Article = new Competition();
        $em = $this->getDoctrine()->getManager();
        $competitions = $em->getRepository('EventsBundle:Competition')->findAll();

        return $this->render('@Events/dashboard/competition.html.twig', array('competitions' => $competitions));
    }
}
