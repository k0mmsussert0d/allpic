import { Generic } from 'rbx';
import { useEffect, useState } from 'react';
import { APIMethods } from '../services/ApiActions';
import { APIResponse, ImagePreviewDetails } from '../types/API';
import Gallery from './../components/Gallery/Gallery';

const Home = () => {

    return (
        <Generic as="div" className="gallery-wrapper">
            <Gallery />
        </Generic>
    );
};

export default Home;