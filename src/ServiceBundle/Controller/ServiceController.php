<?php

namespace ServiceBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class ServiceController extends Controller
{
    public function afficherhebergementAction()
    {
        $em = $this->getDoctrine()->getManager();
        $hebergements= $em->getRepository("ServiceBundle:Hebergement")->findAll();
        return $this->render("@Service/dashboard/hebergement.html.twig", array('hebergements'=>$hebergements));
    }
}
