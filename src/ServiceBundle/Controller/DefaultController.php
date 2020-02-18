<?php

namespace ServiceBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function locationAction()
    {
        return $this->render('@Service/dashboard/location.html.twig');
    }
    public function hebergementAction()
    {
        return $this->render('@Service/dashboard/hebergement.html.twig');
    }
    public function transportAction()
    {
        return $this->render('@Service/dashboard/transport.html.twig');
    }
    public function frontlocationAction()
    {
        return $this->render('@Service/front/location.html.twig');
    }
    public function fronthebergementAction()
    {
        return $this->render('@Service/front/hebergement.html.twig');
    }
    public function fronttransportAction()
    {
        return $this->render('@Service/front/transport.html.twig');
    }
}
