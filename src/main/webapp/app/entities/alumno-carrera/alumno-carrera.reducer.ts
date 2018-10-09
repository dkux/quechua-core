import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAlumnoCarrera, defaultValue } from 'app/shared/model/alumno-carrera.model';

export const ACTION_TYPES = {
  FETCH_ALUMNOCARRERA_LIST: 'alumnoCarrera/FETCH_ALUMNOCARRERA_LIST',
  FETCH_ALUMNOCARRERA: 'alumnoCarrera/FETCH_ALUMNOCARRERA',
  CREATE_ALUMNOCARRERA: 'alumnoCarrera/CREATE_ALUMNOCARRERA',
  UPDATE_ALUMNOCARRERA: 'alumnoCarrera/UPDATE_ALUMNOCARRERA',
  DELETE_ALUMNOCARRERA: 'alumnoCarrera/DELETE_ALUMNOCARRERA',
  RESET: 'alumnoCarrera/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAlumnoCarrera>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type AlumnoCarreraState = Readonly<typeof initialState>;

// Reducer

export default (state: AlumnoCarreraState = initialState, action): AlumnoCarreraState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ALUMNOCARRERA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ALUMNOCARRERA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ALUMNOCARRERA):
    case REQUEST(ACTION_TYPES.UPDATE_ALUMNOCARRERA):
    case REQUEST(ACTION_TYPES.DELETE_ALUMNOCARRERA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ALUMNOCARRERA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ALUMNOCARRERA):
    case FAILURE(ACTION_TYPES.CREATE_ALUMNOCARRERA):
    case FAILURE(ACTION_TYPES.UPDATE_ALUMNOCARRERA):
    case FAILURE(ACTION_TYPES.DELETE_ALUMNOCARRERA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ALUMNOCARRERA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ALUMNOCARRERA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ALUMNOCARRERA):
    case SUCCESS(ACTION_TYPES.UPDATE_ALUMNOCARRERA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ALUMNOCARRERA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/alumno-carreras';

// Actions

export const getEntities: ICrudGetAllAction<IAlumnoCarrera> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ALUMNOCARRERA_LIST,
  payload: axios.get<IAlumnoCarrera>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IAlumnoCarrera> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ALUMNOCARRERA,
    payload: axios.get<IAlumnoCarrera>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAlumnoCarrera> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ALUMNOCARRERA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAlumnoCarrera> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ALUMNOCARRERA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAlumnoCarrera> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ALUMNOCARRERA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
